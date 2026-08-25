package com.dwarfeng.audit.impl.service.operation;

import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.audit.stack.bean.entity.AuditEntryProperty;
import com.dwarfeng.audit.stack.bean.key.AuditEntryPropertyKey;
import com.dwarfeng.audit.stack.cache.AuditEntryCache;
import com.dwarfeng.audit.stack.cache.AuditEntryPropertyCache;
import com.dwarfeng.audit.stack.dao.AuditEntryDao;
import com.dwarfeng.audit.stack.dao.AuditEntryPropertyDao;
import com.dwarfeng.audit.stack.service.AuditEntryPropertyMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuditEntryCrudOperation implements BatchCrudOperation<LongIdKey, AuditEntry> {

    private final AuditEntryDao auditEntryDao;
    private final AuditEntryCache auditEntryCache;
    private final AuditEntryPropertyDao auditEntryPropertyDao;
    private final AuditEntryPropertyCache auditEntryPropertyCache;

    @Value("${com.dwarfeng.audit.cache.timeout.entity.audit_entry}")
    private long auditEntryTimeout;

    public AuditEntryCrudOperation(
            AuditEntryDao auditEntryDao,
            AuditEntryCache auditEntryCache,
            AuditEntryPropertyDao auditEntryPropertyDao,
            AuditEntryPropertyCache auditEntryPropertyCache
    ) {
        this.auditEntryDao = auditEntryDao;
        this.auditEntryCache = auditEntryCache;
        this.auditEntryPropertyDao = auditEntryPropertyDao;
        this.auditEntryPropertyCache = auditEntryPropertyCache;
    }

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return auditEntryCache.exists(key) || auditEntryDao.exists(key);
    }

    @Override
    public AuditEntry get(LongIdKey key) throws Exception {
        if (auditEntryCache.exists(key)) {
            return auditEntryCache.get(key);
        } else {
            if (!auditEntryDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            AuditEntry auditEntry = auditEntryDao.get(key);
            auditEntryCache.push(auditEntry, auditEntryTimeout);
            return auditEntry;
        }
    }

    @Override
    public LongIdKey insert(AuditEntry auditEntry) throws Exception {
        auditEntryCache.push(auditEntry, auditEntryTimeout);
        return auditEntryDao.insert(auditEntry);
    }

    @Override
    public void update(AuditEntry auditEntry) throws Exception {
        auditEntryCache.push(auditEntry, auditEntryTimeout);
        auditEntryDao.update(auditEntry);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        List<AuditEntryProperty> properties = auditEntryPropertyDao.lookup(
                AuditEntryPropertyMaintainService.CHILD_FOR_AUDIT_ENTRY, new Object[]{key}
        );
        List<AuditEntryPropertyKey> propertyKeys = properties.stream()
                .map(AuditEntryProperty::getKey).collect(Collectors.toList());
        if (!propertyKeys.isEmpty()) {
            auditEntryPropertyDao.batchDelete(propertyKeys);
            auditEntryPropertyCache.batchDelete(propertyKeys);
        }

        // 删除 审计条目 自身。
        auditEntryDao.delete(key);
        auditEntryCache.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return auditEntryCache.allExists(keys) || auditEntryDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return auditEntryCache.nonExists(keys) && auditEntryDao.nonExists(keys);
    }

    @Override
    public List<AuditEntry> batchGet(List<LongIdKey> keys) throws Exception {
        if (auditEntryCache.allExists(keys)) {
            return auditEntryCache.batchGet(keys);
        } else {
            if (!auditEntryDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<AuditEntry> auditEntries = auditEntryDao.batchGet(keys);
            auditEntryCache.batchPush(auditEntries, auditEntryTimeout);
            return auditEntries;
        }
    }

    @Override
    public List<LongIdKey> batchInsert(List<AuditEntry> auditEntries) throws Exception {
        auditEntryCache.batchPush(auditEntries, auditEntryTimeout);
        return auditEntryDao.batchInsert(auditEntries);
    }

    @Override
    public void batchUpdate(List<AuditEntry> auditEntries) throws Exception {
        auditEntryCache.batchPush(auditEntries, auditEntryTimeout);
        auditEntryDao.batchUpdate(auditEntries);
    }

    @Override
    public void batchDelete(List<LongIdKey> keys) throws Exception {
        for (LongIdKey key : keys) {
            delete(key);
        }
    }
}
