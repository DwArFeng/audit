package com.dwarfeng.audit.impl.handler.inspreceiver;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.audit.sdk.handler.inspreceiver.AbstractInspectionReceiver;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Kafka 接收器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class KafkaInspectionReceiver extends AbstractInspectionReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaInspectionReceiver.class);

    public static final String RECEIVER_TYPE = "kafka";

    private final KafkaListenerEndpointRegistry registry;

    @Value("${com.dwarfeng.audit.inspection_receiver.kafka.listener_id}")
    private String listenerId;

    @SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "RedundantSuppression"})
    public KafkaInspectionReceiver(KafkaListenerEndpointRegistry registry) {
        super(RECEIVER_TYPE);
        this.registry = registry;
    }

    @Override
    protected void doStart() {
        MessageListenerContainer container = requireContainer();
        if (!container.isRunning()) {
            container.start();
        }
        container.resume();
    }

    @Override
    protected void doStop() {
        requireContainer().stop();
    }

    @KafkaListener(
            id = "${com.dwarfeng.audit.inspection_receiver.kafka.listener_id}",
            containerFactory = "inspectionReceiverKafka.kafkaListenerContainerFactory",
            topics = "${com.dwarfeng.audit.inspection_receiver.kafka.listener_topic}"
    )
    public void handleConsumerRecordsPolled(
            List<ConsumerRecord<String, String>> records, Consumer<String, String> consumer, Acknowledgment ack
    ) {
        for (ConsumerRecord<String, String> record : records) {
            try {
                LongIdKey inspectionKey = FastJsonLongIdKey.toStackBean(
                        JSON.parseObject(record.value(), FastJsonLongIdKey.class)
                );
                context.execute(inspectionKey);
            } catch (Exception e) {
                LOGGER.warn("接收器无法处理 Kafka 消息，将忽略自动审计执行 1 次", e);
            }
        }
        ack.acknowledge();
    }

    private MessageListenerContainer requireContainer() {
        MessageListenerContainer container = registry.getListenerContainer(listenerId);
        if (Objects.isNull(container)) {
            throw new IllegalStateException("找不到 Kafka listener container " + listenerId);
        }
        return container;
    }

    @Configuration("inspectionReceiverKafka.kafkaConfiguration")
    @EnableKafka
    public static class KafkaConfiguration {

        @Value("${com.dwarfeng.audit.inspection_receiver.kafka.bootstrap_servers}")
        private String bootstrapServers;
        @Value("${com.dwarfeng.audit.inspection_receiver.kafka.session_timeout_ms}")
        private int sessionTimeoutMs;
        @Value("${com.dwarfeng.audit.inspection_receiver.kafka.auto_offset_reset}")
        private String autoOffsetReset;
        @Value("${com.dwarfeng.audit.inspection_receiver.kafka.concurrency}")
        private int concurrency;
        @Value("${com.dwarfeng.audit.inspection_receiver.kafka.poll_timeout}")
        private int pollTimeout;
        @Value("${com.dwarfeng.audit.inspection_receiver.kafka.max_poll_records}")
        private int maxPollRecords;
        @Value("${com.dwarfeng.audit.inspection_receiver.kafka.max_poll_interval_ms}")
        private int maxPollIntervalMs;

        @SuppressWarnings("DuplicatedCode")
        @Bean("inspectionReceiverKafka.consumerFactory")
        public ConsumerFactory<String, String> consumerFactory() {
            Map<String, Object> properties = new HashMap<>();
            properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMs);
            properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
            properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
            properties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollIntervalMs);
            properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
            return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), new StringDeserializer());
        }

        @Bean("inspectionReceiverKafka.kafkaListenerContainerFactory")
        public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
        kafkaListenerContainerFactory() {
            ConcurrentKafkaListenerContainerFactory<String, String> factory =
                    new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactory());
            factory.setConcurrency(concurrency);
            factory.getContainerProperties().setPollTimeout(pollTimeout);
            factory.setAutoStartup(false);
            factory.setBatchListener(true);
            factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
            return factory;
        }
    }
}
