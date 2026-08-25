package com.dwarfeng.audit.impl.service.operation;

import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.audit.stack.bean.entity.AuditPropertyIndicator;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.audit.stack.cache.AuditCategoryCache;
import com.dwarfeng.audit.stack.cache.AuditEntryCache;
import com.dwarfeng.audit.stack.cache.AuditPropertyIndicatorCache;
import com.dwarfeng.audit.stack.dao.AuditCategoryDao;
import com.dwarfeng.audit.stack.dao.AuditEntryDao;
import com.dwarfeng.audit.stack.dao.AuditPropertyIndicatorDao;
import com.dwarfeng.audit.stack.service.AuditEntryMaintainService;
import com.dwarfeng.audit.stack.service.AuditPropertyIndicatorMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuditCategoryCrudOperation implements BatchCrudOperation<StringIdKey, AuditCategory> {

    private final AuditCategoryDao auditCategoryDao;
    private final AuditCategoryCache auditCategoryCache;
    private final AuditPropertyIndicatorDao auditPropertyIndicatorDao;
    private final AuditPropertyIndicatorCache auditPropertyIndicatorCache;
    private final AuditEntryDao auditEntryDao;
    private final AuditEntryCache auditEntryCache;

    @Value("${com.dwarfeng.audit.cache.timeout.entity.audit_category}")
    private long auditCategoryTimeout;

    public AuditCategoryCrudOperation(
            AuditCategoryDao auditCategoryDao,
            AuditCategoryCache auditCategoryCache,
            AuditPropertyIndicatorDao auditPropertyIndicatorDao,
            AuditPropertyIndicatorCache auditPropertyIndicatorCache,
            AuditEntryDao auditEntryDao,
            AuditEntryCache auditEntryCache
    ) {
        this.auditCategoryDao = auditCategoryDao;
        this.auditCategoryCache = auditCategoryCache;
        this.auditPropertyIndicatorDao = auditPropertyIndicatorDao;
        this.auditPropertyIndicatorCache = auditPropertyIndicatorCache;
        this.auditEntryDao = auditEntryDao;
        this.auditEntryCache = auditEntryCache;
    }

    @Override
    public boolean exists(StringIdKey key) throws Exception {
        return auditCategoryCache.exists(key) || auditCategoryDao.exists(key);
    }

    @Override
    public AuditCategory get(StringIdKey key) throws Exception {
        if (auditCategoryCache.exists(key)) {
            return auditCategoryCache.get(key);
        } else {
            if (!auditCategoryDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            AuditCategory auditCategory = auditCategoryDao.get(key);
            auditCategoryCache.push(auditCategory, auditCategoryTimeout);
            return auditCategory;
        }
    }

    @Override
    public StringIdKey insert(AuditCategory auditCategory) throws Exception {
        auditCategoryCache.push(auditCategory, auditCategoryTimeout);
        return auditCategoryDao.insert(auditCategory);
    }

    @Override
    public void update(AuditCategory auditCategory) throws Exception {
        auditCategoryCache.push(auditCategory, auditCategoryTimeout);
        auditCategoryDao.update(auditCategory);
    }

    @Override
    public void delete(StringIdKey key) throws Exception {
        List<AuditPropertyIndicator> indicators = auditPropertyIndicatorDao.lookup(
                AuditPropertyIndicatorMaintainService.CHILD_FOR_AUDIT_CATEGORY, new Object[]{key}
        );
        List<AuditPropertyIndicatorKey> indicatorKeys = indicators.stream()
                .map(AuditPropertyIndicator::getKey).collect(Collectors.toList());
        if (!indicatorKeys.isEmpty()) {
            auditPropertyIndicatorDao.batchDelete(indicatorKeys);
            auditPropertyIndicatorCache.batchDelete(indicatorKeys);
        }

        List<AuditEntry> entries = auditEntryDao.lookup(
                AuditEntryMaintainService.CHILD_FOR_AUDIT_CATEGORY, new Object[]{key}
        );
        for (AuditEntry entry : entries) {
            entry.setCategoryKey(null);
        }
        if (!entries.isEmpty()) {
            auditEntryDao.batchUpdate(entries);
            auditEntryCache.batchDelete(entries.stream().map(AuditEntry::getKey).collect(Collectors.toList()));
        }

        // 删除 审计类别 自身。
        auditCategoryDao.delete(key);
        auditCategoryCache.delete(key);
    }

    @Override
    public boolean allExists(List<StringIdKey> keys) throws Exception {
        return auditCategoryCache.allExists(keys) || auditCategoryDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<StringIdKey> keys) throws Exception {
        return auditCategoryCache.nonExists(keys) && auditCategoryDao.nonExists(keys);
    }

    @Override
    public List<AuditCategory> batchGet(List<StringIdKey> keys) throws Exception {
        if (auditCategoryCache.allExists(keys)) {
            return auditCategoryCache.batchGet(keys);
        } else {
            if (!auditCategoryDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<AuditCategory> auditCategories = auditCategoryDao.batchGet(keys);
            auditCategoryCache.batchPush(auditCategories, auditCategoryTimeout);
            return auditCategories;
        }
    }

    @Override
    public List<StringIdKey> batchInsert(List<AuditCategory> auditCategories) throws Exception {
        auditCategoryCache.batchPush(auditCategories, auditCategoryTimeout);
        return auditCategoryDao.batchInsert(auditCategories);
    }

    @Override
    public void batchUpdate(List<AuditCategory> auditCategories) throws Exception {
        auditCategoryCache.batchPush(auditCategories, auditCategoryTimeout);
        auditCategoryDao.batchUpdate(auditCategories);
    }

    @Override
    public void batchDelete(List<StringIdKey> keys) throws Exception {
        for (StringIdKey key : keys) {
            delete(key);
        }
    }
}
