package com.dwarfeng.audit.impl.handler.consumer;

import com.dwarfeng.audit.impl.handler.Consumer;
import com.dwarfeng.audit.stack.bean.dto.AuditRecordData;
import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.audit.stack.bean.entity.AuditEntryProperty;
import com.dwarfeng.audit.stack.service.AuditEntryMaintainService;
import com.dwarfeng.audit.stack.service.AuditEntryPropertyMaintainService;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 审计记录消费者。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class AuditRecordConsumer implements Consumer<AuditRecordData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditRecordConsumer.class);

    private final Persister persister;

    public AuditRecordConsumer(Persister persister) {
        this.persister = persister;
    }

    @Override
    public void consume(List<AuditRecordData> datas) {
        // 特殊情况判断: 如果 datas 为 null 或为空，则直接返回。
        if (Objects.isNull(datas) || datas.isEmpty()) {
            return;
        }

        // 优先尝试批量持久化审计数据，如果批量持久化失败，则尝试逐条持久化审计数据。
        try {
            persister.persist(datas);
            return;
        } catch (Exception e) {
            LOGGER.error("批量记录审计数据失败，改用单条记录策略", e);
        }

        // 定义列表，用于存放持久化失败的审计数据。
        List<AuditRecordData> failedList = new ArrayList<>();

        // 遍历 datas 中的所有审计数据，逐条持久化审计数据。
        for (AuditRecordData data : datas) {
            try {
                persister.persist(Collections.singletonList(data));
            } catch (Exception e) {
                LOGGER.error("审计数据持久化失败, 放弃对该审计数据的持久化: {}", data, e);
                failedList.add(data);
            }
        }

        // 如果有持久化失败的审计数据，则持久化日志。
        if (!failedList.isEmpty()) {
            LOGGER.error("持久化审计数据时发生异常, 最多 {} 个审计数据信息丢失", failedList.size());
            failedList.forEach(record -> LOGGER.debug(Objects.toString(record)));
        }
    }

    /**
     * 审计记录事务持久化器。
     *
     * @author DwArFeng
     * @since 1.0.0-beta
     */
    public static class Persister {

        private final AuditEntryMaintainService auditEntryMaintainService;
        private final AuditEntryPropertyMaintainService auditEntryPropertyMaintainService;

        public Persister(
                AuditEntryMaintainService auditEntryMaintainService,
                AuditEntryPropertyMaintainService auditEntryPropertyMaintainService
        ) {
            this.auditEntryMaintainService = auditEntryMaintainService;
            this.auditEntryPropertyMaintainService = auditEntryPropertyMaintainService;
        }

        @Transactional(
                transactionManager = "hibernateTransactionManager",
                rollbackFor = Exception.class,
                propagation = Propagation.REQUIRES_NEW
        )
        public void persist(List<AuditRecordData> datas) throws HandlerException {
            List<AuditEntry> auditEntries = new ArrayList<>(datas.size());
            List<AuditEntryProperty> auditEntryProperties = new ArrayList<>();
            for (AuditRecordData data : datas) {
                if (data == null) {
                    continue;
                }
                if (data.getAuditEntry() != null) {
                    auditEntries.add(data.getAuditEntry());
                }
                if (data.getAuditEntryProperties() != null) {
                    auditEntryProperties.addAll(data.getAuditEntryProperties());
                }
            }
            try {
                if (!auditEntries.isEmpty()) {
                    auditEntryMaintainService.batchInsert(auditEntries);
                }
                if (!auditEntryProperties.isEmpty()) {
                    auditEntryPropertyMaintainService.batchInsert(auditEntryProperties);
                }
            } catch (ServiceException e) {
                throw new HandlerException(e);
            }
        }
    }
}
