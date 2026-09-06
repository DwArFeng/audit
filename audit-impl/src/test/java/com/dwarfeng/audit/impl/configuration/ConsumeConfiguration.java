package com.dwarfeng.audit.impl.configuration;

import com.dwarfeng.audit.impl.handler.ConsumeHandlerImpl;
import com.dwarfeng.audit.impl.handler.Consumer;
import com.dwarfeng.audit.impl.handler.consumer.AuditRecordConsumer;
import com.dwarfeng.audit.impl.handler.consumer.InspectionReceiveConsumer;
import com.dwarfeng.audit.stack.bean.dto.AuditRecordData;
import com.dwarfeng.audit.stack.handler.ConsumeHandler;
import com.dwarfeng.audit.stack.handler.InspectionJobHandler;
import com.dwarfeng.audit.stack.service.AuditEntryMaintainService;
import com.dwarfeng.audit.stack.service.AuditEntryPropertyMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.ArrayList;

@Configuration
public class ConsumeConfiguration {

    private final ThreadPoolTaskExecutor executor;
    private final ThreadPoolTaskScheduler scheduler;

    @Value("${com.dwarfeng.audit.consume.threshold.warn}")
    private double warnThreshold;

    @Value("${com.dwarfeng.audit.consume.audit_record.consumer_thread}")
    private int auditRecordConsumerThread;
    @Value("${com.dwarfeng.audit.consume.audit_record.buffer_size}")
    private int auditRecordBufferSize;
    @Value("${com.dwarfeng.audit.consume.audit_record.batch_size}")
    private int auditRecordBatchSize;
    @Value("${com.dwarfeng.audit.consume.audit_record.max_idle_time}")
    private long auditRecordMaxIdleTime;

    @Value("${com.dwarfeng.audit.consume.inspection_receive.consumer_thread}")
    private int inspectionReceiveConsumerThread;
    @Value("${com.dwarfeng.audit.consume.inspection_receive.buffer_size}")
    private int inspectionReceiveBufferSize;
    @Value("${com.dwarfeng.audit.consume.inspection_receive.batch_size}")
    private int inspectionReceiveBatchSize;
    @Value("${com.dwarfeng.audit.consume.inspection_receive.max_idle_time}")
    private long inspectionReceiveMaxIdleTime;

    public ConsumeConfiguration(
            ThreadPoolTaskExecutor executor,
            ThreadPoolTaskScheduler scheduler
    ) {
        this.executor = executor;
        this.scheduler = scheduler;
    }

    @Bean
    public AuditRecordConsumer.Persister auditRecordPersistence(
            AuditEntryMaintainService auditEntryMaintainService,
            AuditEntryPropertyMaintainService auditEntryPropertyMaintainService
    ) {
        return new AuditRecordConsumer.Persister(auditEntryMaintainService, auditEntryPropertyMaintainService);
    }

    @Bean
    public Consumer<AuditRecordData> auditRecordConsumer(AuditRecordConsumer.Persister persister) {
        return new AuditRecordConsumer(persister);
    }

    @Bean
    public ConsumeHandler<AuditRecordData> auditRecordConsumeHandler(Consumer<AuditRecordData> auditRecordConsumer) {
        ConsumeHandlerImpl<AuditRecordData> consumeHandler = new ConsumeHandlerImpl<>(
                executor,
                scheduler,
                new ArrayList<>(),
                new ArrayList<>(),
                auditRecordConsumer,
                auditRecordConsumerThread,
                warnThreshold
        );
        consumeHandler.setBufferParameters(auditRecordBufferSize, auditRecordBatchSize, auditRecordMaxIdleTime);
        return consumeHandler;
    }

    @Bean
    public Consumer<LongIdKey> inspectionReceiveConsumer(InspectionJobHandler inspectionJobHandler) {
        return new InspectionReceiveConsumer(inspectionJobHandler);
    }

    @Bean
    public ConsumeHandler<LongIdKey> inspectionReceiveConsumeHandler(Consumer<LongIdKey> inspectionReceiveConsumer) {
        ConsumeHandlerImpl<LongIdKey> consumeHandler = new ConsumeHandlerImpl<>(
                executor,
                scheduler,
                new ArrayList<>(),
                new ArrayList<>(),
                inspectionReceiveConsumer,
                inspectionReceiveConsumerThread,
                warnThreshold
        );
        consumeHandler.setBufferParameters(
                inspectionReceiveBufferSize,
                inspectionReceiveBatchSize,
                inspectionReceiveMaxIdleTime
        );
        return consumeHandler;
    }
}
