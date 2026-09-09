package com.dwarfeng.audit.impl.handler.pusher;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.audit.sdk.bean.dto.FastJsonPurgeFinishedResult;
import com.dwarfeng.audit.sdk.bean.entity.FastJsonInspectionAlarm;
import com.dwarfeng.audit.sdk.bean.entity.FastJsonInspectionTask;
import com.dwarfeng.audit.sdk.handler.pusher.AbstractPusher;
import com.dwarfeng.audit.stack.bean.dto.PurgeFinishedResult;
import com.dwarfeng.audit.stack.bean.entity.InspectionAlarm;
import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地 Kafka 推送器。
 *
 * <p>
 * 该推送器使用独立的 Kafka 生产者和事务管理器发送事件，避免与项目中的其它 Kafka 组件共享事务资源。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
@Component
public class NativeKafkaPusher extends AbstractPusher {

    public static final String PUSHER_TYPE = "kafka.native";

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${com.dwarfeng.audit.pusher.kafka.native.topic.audit_record_reset}")
    private String auditRecordResetTopic;
    @Value("${com.dwarfeng.audit.pusher.kafka.native.topic.inspection_supervise_reset}")
    private String inspectionSuperviseResetTopic;
    @Value("${com.dwarfeng.audit.pusher.kafka.native.topic.inspection_task_finished}")
    private String inspectionTaskFinishedTopic;
    @Value("${com.dwarfeng.audit.pusher.kafka.native.topic.inspection_task_failed}")
    private String inspectionTaskFailedTopic;
    @Value("${com.dwarfeng.audit.pusher.kafka.native.topic.inspection_task_expired}")
    private String inspectionTaskExpiredTopic;
    @Value("${com.dwarfeng.audit.pusher.kafka.native.topic.inspection_task_died}")
    private String inspectionTaskDiedTopic;
    @Value("${com.dwarfeng.audit.pusher.kafka.native.topic.inspection_job_reset}")
    private String inspectionJobResetTopic;
    @Value("${com.dwarfeng.audit.pusher.kafka.native.topic.inspection_alarm_created}")
    private String inspectionAlarmCreatedTopic;
    @Value("${com.dwarfeng.audit.pusher.kafka.native.topic.purge_finished}")
    private String purgeFinishedTopic;
    @Value("${com.dwarfeng.audit.pusher.kafka.native.topic.purge_failed}")
    private String purgeFailedTopic;

    public NativeKafkaPusher(
            @Qualifier("nativeKafkaPusher.kafkaTemplate") KafkaTemplate<String, String> kafkaTemplate
    ) {
        super(PUSHER_TYPE);
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void auditRecordReset() {
        kafkaTemplate.send(auditRecordResetTopic, StringUtils.EMPTY);
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void inspectionSuperviseReset() {
        kafkaTemplate.send(inspectionSuperviseResetTopic, StringUtils.EMPTY);
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void inspectionTaskFinished(InspectionTask inspectionTask) {
        kafkaTemplate.send(
                inspectionTaskFinishedTopic,
                JSON.toJSONString(FastJsonInspectionTask.of(inspectionTask))
        );
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void inspectionTaskFailed(InspectionTask inspectionTask) {
        kafkaTemplate.send(
                inspectionTaskFailedTopic,
                JSON.toJSONString(FastJsonInspectionTask.of(inspectionTask))
        );
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void inspectionTaskExpired(InspectionTask inspectionTask) {
        kafkaTemplate.send(
                inspectionTaskExpiredTopic,
                JSON.toJSONString(FastJsonInspectionTask.of(inspectionTask))
        );
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void inspectionTaskDied(InspectionTask inspectionTask) {
        kafkaTemplate.send(
                inspectionTaskDiedTopic,
                JSON.toJSONString(FastJsonInspectionTask.of(inspectionTask))
        );
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void inspectionJobReset() {
        kafkaTemplate.send(inspectionJobResetTopic, StringUtils.EMPTY);
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void inspectionAlarmCreated(InspectionAlarm inspectionAlarm) {
        kafkaTemplate.send(
                inspectionAlarmCreatedTopic,
                JSON.toJSONString(FastJsonInspectionAlarm.of(inspectionAlarm))
        );
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void purgeFinished(PurgeFinishedResult result) {
        kafkaTemplate.send(purgeFinishedTopic, JSON.toJSONString(FastJsonPurgeFinishedResult.of(result)));
    }

    @Transactional(transactionManager = "nativeKafkaPusher.kafkaTransactionManager")
    @Override
    public void purgeFailed() {
        kafkaTemplate.send(purgeFailedTopic, StringUtils.EMPTY);
    }

    @Override
    public String toString() {
        return "NativeKafkaPusher{" +
                "kafkaTemplate=" + kafkaTemplate +
                ", auditRecordResetTopic='" + auditRecordResetTopic + '\'' +
                ", inspectionSuperviseResetTopic='" + inspectionSuperviseResetTopic + '\'' +
                ", inspectionTaskFinishedTopic='" + inspectionTaskFinishedTopic + '\'' +
                ", inspectionTaskFailedTopic='" + inspectionTaskFailedTopic + '\'' +
                ", inspectionTaskExpiredTopic='" + inspectionTaskExpiredTopic + '\'' +
                ", inspectionTaskDiedTopic='" + inspectionTaskDiedTopic + '\'' +
                ", inspectionJobResetTopic='" + inspectionJobResetTopic + '\'' +
                ", inspectionAlarmCreatedTopic='" + inspectionAlarmCreatedTopic + '\'' +
                ", purgeFinishedTopic='" + purgeFinishedTopic + '\'' +
                ", purgeFailedTopic='" + purgeFailedTopic + '\'' +
                ", pusherType='" + pusherType + '\'' +
                '}';
    }

    /**
     * Kafka 推送器配置。
     *
     * <p>
     * 该配置为本地 Kafka 推送器提供独立的生产者工厂、Kafka 模板以及事务管理器。
     *
     * @author DwArFeng
     * @since 1.0.0-beta
     */
    @Configuration
    public static class KafkaPusherConfiguration {

        private static final Logger LOGGER = LoggerFactory.getLogger(KafkaPusherConfiguration.class);

        @Value("${com.dwarfeng.audit.pusher.kafka.native.bootstrap_servers}")
        private String producerBootstrapServers;
        @Value("${com.dwarfeng.audit.pusher.kafka.native.retries}")
        private int retries;
        @Value("${com.dwarfeng.audit.pusher.kafka.native.linger}")
        private long linger;
        @Value("${com.dwarfeng.audit.pusher.kafka.native.buffer_memory}")
        private long bufferMemory;
        @Value("${com.dwarfeng.audit.pusher.kafka.native.batch_size}")
        private int batchSize;
        @Value("${com.dwarfeng.audit.pusher.kafka.native.acks}")
        private String acks;
        @Value("${com.dwarfeng.audit.pusher.kafka.native.transaction_prefix}")
        private String transactionPrefix;

        @SuppressWarnings("DuplicatedCode")
        @Bean("nativeKafkaPusher.producerProperties")
        public Map<String, Object> producerProperties() {
            LOGGER.info("配置 Kafka 生产者属性...");
            Map<String, Object> properties = new HashMap<>();
            properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerBootstrapServers);
            properties.put(ProducerConfig.RETRIES_CONFIG, retries);
            properties.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
            properties.put(ProducerConfig.LINGER_MS_CONFIG, linger);
            properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
            properties.put(ProducerConfig.ACKS_CONFIG, acks);
            LOGGER.debug("Kafka 生产者属性配置完成...");
            return properties;
        }

        @Bean("nativeKafkaPusher.producerFactory")
        public ProducerFactory<String, String> producerFactory() {
            LOGGER.info("配置 Kafka 生产者工厂...");
            DefaultKafkaProducerFactory<String, String> factory =
                    new DefaultKafkaProducerFactory<>(producerProperties());
            factory.setTransactionIdPrefix(transactionPrefix);
            factory.setKeySerializer(new StringSerializer());
            factory.setValueSerializer(new StringSerializer());
            LOGGER.debug("Kafka 生产者工厂配置完成");
            return factory;
        }

        @Bean("nativeKafkaPusher.kafkaTemplate")
        public KafkaTemplate<String, String> kafkaTemplate() {
            LOGGER.info("生成 KafkaTemplate...");
            KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory(), true);
            LOGGER.debug("KafkaTemplate 生成完成...");
            return kafkaTemplate;
        }

        @Bean("nativeKafkaPusher.kafkaTransactionManager")
        public KafkaTransactionManager<String, String> kafkaTransactionManager() {
            LOGGER.info("生成 KafkaTransactionManager...");
            KafkaTransactionManager<String, String> kafkaTransactionManager =
                    new KafkaTransactionManager<>(producerFactory());
            LOGGER.debug("KafkaTransactionManager 生成完成...");
            return kafkaTransactionManager;
        }
    }
}
