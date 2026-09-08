package com.dwarfeng.audit.impl.handler.inspdriver;

import com.dwarfeng.audit.sdk.handler.InspectionDriverProvider;
import com.dwarfeng.audit.sdk.handler.inspdriver.AbstractInspectionDriver;
import com.dwarfeng.audit.stack.bean.entity.InspectionDriverInfo;
import com.dwarfeng.audit.stack.exception.InspectionDriverException;
import com.dwarfeng.audit.stack.handler.InspectionDriver;
import com.dwarfeng.dcti.impl.handler.DctiHandlerImpl;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.dcti.stack.handler.DctiHandler;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.kafka.clients.consumer.ConsumerConfig;
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

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Dcti 标准数据采集接口 Kafka 自动审计驱动提供器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class DctiKafkaInspectionDriverProvider implements InspectionDriverProvider {

    public static final String SUPPORT_TYPE = "dcti_kafka_inspection_driver";

    private final DctiKafkaInspectionDriver dctiKafkaInspectionDriver;

    public DctiKafkaInspectionDriverProvider(DctiKafkaInspectionDriver dctiKafkaInspectionDriver) {
        this.dctiKafkaInspectionDriver = dctiKafkaInspectionDriver;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(SUPPORT_TYPE, type);
    }

    @Override
    public InspectionDriver provide() {
        return dctiKafkaInspectionDriver;
    }

    @Component
    public static class DctiKafkaInspectionDriver extends AbstractInspectionDriver {

        private static final Logger LOGGER = LoggerFactory.getLogger(DctiKafkaInspectionDriver.class);

        private final DctiHandler dctiHandler;
        private final KafkaListenerEndpointRegistry registry;

        @Value("${com.dwarfeng.audit.inspection_driver.kafka.dcti.listener_id}")
        private String listenerId;

        private final Map<Long, Set<LongIdKey>> registerMap = new HashMap<>();
        private final Lock lock = new ReentrantLock();

        private boolean kafkaListenerContainerStartFlag = false;

        public DctiKafkaInspectionDriver(DctiHandler dctiHandler, KafkaListenerEndpointRegistry registry) {
            this.registry = registry;
            this.dctiHandler = dctiHandler;
        }

        @Override
        public void register(InspectionDriverInfo driverInfo) throws InspectionDriverException {
            lock.lock();
            try {
                LongIdKey inspectionKey = driverInfo.getInspectionKey();
                Long dataInfoKey = Long.parseLong(driverInfo.getParam());
                registerMap.computeIfAbsent(dataInfoKey, key -> new HashSet<>()).add(inspectionKey);
                mayStartKafkaListenerContainer();
            } catch (Exception e) {
                throw new InspectionDriverException(e);
            } finally {
                lock.unlock();
            }
        }

        private void mayStartKafkaListenerContainer() {
            if (kafkaListenerContainerStartFlag) {
                return;
            }
            LOGGER.info("Kafka 侦听容器启动...");
            MessageListenerContainer listenerContainer = registry.getListenerContainer(listenerId);
            if (Objects.isNull(listenerContainer)) {
                throw new IllegalStateException("找不到 kafka listener container " + listenerId);
            }
            if (!listenerContainer.isRunning()) {
                listenerContainer.start();
            }
            listenerContainer.resume();
            kafkaListenerContainerStartFlag = true;
        }

        @Override
        public void unregisterAll() {
            lock.lock();
            try {
                registerMap.clear();
                mayStopKafkaListenerContainer();
            } finally {
                lock.unlock();
            }
        }

        private void mayStopKafkaListenerContainer() {
            if (!kafkaListenerContainerStartFlag) {
                return;
            }
            LOGGER.info("Kafka 侦听容器停止...");
            MessageListenerContainer listenerContainer = registry.getListenerContainer(listenerId);
            if (Objects.isNull(listenerContainer)) {
                throw new IllegalStateException("找不到 kafka listener container " + listenerId);
            }
            listenerContainer.pause();
            kafkaListenerContainerStartFlag = false;
        }

        @KafkaListener(
                id = "${com.dwarfeng.audit.inspection_driver.kafka.dcti.listener_id}",
                containerFactory = "dctiKafkaInspectionDriver.kafkaListenerContainerFactory",
                topics = "${com.dwarfeng.audit.inspection_driver.kafka.dcti.listener_topic}"
        )
        public void handleDataInfo(String message, Acknowledgment ack) {
            lock.lock();
            try {
                ack.acknowledge();
                DataInfo dataInfo = dctiHandler.fromMessage(message);
                Long dataInfoKey = dataInfo.getPointLongId();
                Set<LongIdKey> inspectionKeys = registerMap.get(dataInfoKey);
                if (Objects.isNull(inspectionKeys)) {
                    return;
                }
                for (LongIdKey inspectionKey : inspectionKeys) {
                    try {
                        context.execute(inspectionKey);
                    } catch (Exception e) {
                        LOGGER.warn("自动审计 {} 执行动作时出现异常，将继续处理其余自动审计", inspectionKey, e);
                    }
                }
            } catch (Exception e) {
                LOGGER.warn("处理 dcti dataInfo 时出现异常，将忽略自动审计驱动动作 1 次，异常信息如下", e);
            } finally {
                lock.unlock();
            }
        }

        @Override
        public String toString() {
            return "DctiKafkaInspectionDriver{" +
                    "context=" + context +
                    '}';
        }
    }

    @Configuration
    @EnableKafka
    public static class KafkaInspectionDriverConfiguration {

        private static final Logger LOGGER = LoggerFactory.getLogger(KafkaInspectionDriverConfiguration.class);

        @Value("${com.dwarfeng.audit.inspection_driver.kafka.dcti.bootstrap_servers}")
        private String consumerBootstrapServers;
        @Value("${com.dwarfeng.audit.inspection_driver.kafka.dcti.session_timeout_ms}")
        private int sessionTimeoutMs;
        @Value("${com.dwarfeng.audit.inspection_driver.kafka.dcti.auto_offset_reset}")
        private String autoOffsetReset;
        @Value("${com.dwarfeng.audit.inspection_driver.kafka.dcti.concurrency}")
        private int concurrency;
        @Value("${com.dwarfeng.audit.inspection_driver.kafka.dcti.poll_timeout}")
        private int pollTimeout;
        @Value("${com.dwarfeng.audit.inspection_driver.kafka.dcti.max_poll_records}")
        private int maxPollRecords;
        @Value("${com.dwarfeng.audit.inspection_driver.kafka.dcti.max_poll_interval_ms}")
        private int maxPollIntervalMs;

        @Bean("dctiKafkaInspectionDriver.dctiHandler")
        public DctiHandler dctiHandler() {
            return new DctiHandlerImpl();
        }

        @SuppressWarnings("DuplicatedCode")
        @Bean("dctiKafkaInspectionDriver.consumerProperties")
        public Map<String, Object> consumerProperties() {
            LOGGER.debug("配置 Kafka 消费者属性...");
            Map<String, Object> props = new HashMap<>();

            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerBootstrapServers);
            props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMs);
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
            props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
            props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollIntervalMs);
            props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

            LOGGER.debug("Kafka 消费者属性配置完成...");
            return props;
        }

        @SuppressWarnings("DuplicatedCode")
        @Bean("dctiKafkaInspectionDriver.consumerFactory")
        public ConsumerFactory<String, String> consumerFactory() {
            LOGGER.debug("配置 Kafka 消费者工厂...");
            Map<String, Object> properties = consumerProperties();
            DefaultKafkaConsumerFactory<String, String> factory = new DefaultKafkaConsumerFactory<>(properties);
            factory.setKeyDeserializer(new StringDeserializer());
            factory.setValueDeserializer(new StringDeserializer());
            LOGGER.debug("Kafka 消费者工厂配置完成");
            return factory;
        }

        @SuppressWarnings("DuplicatedCode")
        @Bean("dctiKafkaInspectionDriver.kafkaListenerContainerFactory")
        public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
        kafkaListenerContainerFactory() {
            LOGGER.debug("配置 Kafka 侦听容器工厂...");
            ConsumerFactory<String, String> consumerFactory = consumerFactory();
            ConcurrentKafkaListenerContainerFactory<String, String> factory =
                    new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactory);
            factory.setConcurrency(concurrency);
            factory.getContainerProperties().setPollTimeout(pollTimeout);
            factory.setAutoStartup(false);
            factory.setBatchListener(true);
            factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
            LOGGER.info("配置 Kafka 侦听容器工厂...");
            return factory;
        }
    }
}
