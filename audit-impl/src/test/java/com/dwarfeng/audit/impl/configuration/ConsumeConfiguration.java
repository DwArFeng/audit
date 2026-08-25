package com.dwarfeng.audit.impl.configuration;

import com.dwarfeng.audit.impl.handler.ConsumeHandlerImpl;
import com.dwarfeng.audit.impl.handler.Consumer;
import com.dwarfeng.audit.impl.handler.consumer.AuditRecordConsumer;
import com.dwarfeng.audit.stack.bean.dto.AuditRecordData;
import com.dwarfeng.audit.stack.handler.ConsumeHandler;
import com.dwarfeng.audit.stack.service.AuditEntryMaintainService;
import com.dwarfeng.audit.stack.service.AuditEntryPropertyMaintainService;
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
}
