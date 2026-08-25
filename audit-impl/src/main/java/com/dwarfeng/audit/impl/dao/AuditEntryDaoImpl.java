package com.dwarfeng.audit.impl.dao;

import com.dwarfeng.audit.impl.bean.entity.HibernateAuditEntry;
import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.audit.stack.dao.AuditEntryDao;
import com.dwarfeng.audit.stack.service.AuditEntryMaintainService;
import com.dwarfeng.subgrade.impl.dao.HibernateHqlPresetLookupDao;
import com.dwarfeng.subgrade.impl.dao.HibernatePresetLookupDao;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.exception.DaoException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Repository
public class AuditEntryDaoImpl implements AuditEntryDao {

    private final BatchBaseDao<LongIdKey, AuditEntry> batchBaseDao;
    private final EntireLookupDao<AuditEntry> entireLookupDao;
    private final HibernatePresetLookupDao<AuditEntry, HibernateAuditEntry> presetLookupDao;
    private final HibernateHqlPresetLookupDao<AuditEntry, HibernateAuditEntry> hqlPresetLookupDao;

    public AuditEntryDaoImpl(
            BatchBaseDao<LongIdKey, AuditEntry> batchBaseDao,
            EntireLookupDao<AuditEntry> entireLookupDao,
            HibernatePresetLookupDao<AuditEntry, HibernateAuditEntry> presetLookupDao,
            HibernateHqlPresetLookupDao<AuditEntry, HibernateAuditEntry> hqlPresetLookupDao
    ) {
        this.batchBaseDao = batchBaseDao;
        this.entireLookupDao = entireLookupDao;
        this.presetLookupDao = presetLookupDao;
        this.hqlPresetLookupDao = hqlPresetLookupDao;
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public LongIdKey insert(AuditEntry element) throws DaoException {
        return batchBaseDao.insert(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void update(AuditEntry element) throws DaoException {
        batchBaseDao.update(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void delete(LongIdKey key) throws DaoException {
        batchBaseDao.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean exists(LongIdKey key) throws DaoException {
        return batchBaseDao.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public AuditEntry get(LongIdKey key) throws DaoException {
        return batchBaseDao.get(key);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public List<LongIdKey> batchInsert(@SkipRecord List<AuditEntry> elements) throws DaoException {
        return batchBaseDao.batchInsert(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchUpdate(@SkipRecord List<AuditEntry> elements) throws DaoException {
        batchBaseDao.batchUpdate(elements);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchDelete(@SkipRecord List<LongIdKey> keys) throws DaoException {
        batchBaseDao.batchDelete(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean allExists(@SkipRecord List<LongIdKey> keys) throws DaoException {
        return batchBaseDao.allExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean nonExists(@SkipRecord List<LongIdKey> keys) throws DaoException {
        return batchBaseDao.nonExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<AuditEntry> batchGet(@SkipRecord List<LongIdKey> keys) throws DaoException {
        return batchBaseDao.batchGet(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<AuditEntry> lookup() throws DaoException {
        return entireLookupDao.lookup();
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<AuditEntry> lookup(PagingInfo pagingInfo) throws DaoException {
        return entireLookupDao.lookup(pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public int lookupCount() throws DaoException {
        return entireLookupDao.lookupCount();
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<AuditEntry> lookup(String preset, Object[] objs) throws DaoException {
        return isHqlPreset(preset) ? hqlPresetLookupDao.lookup(preset, objs) : presetLookupDao.lookup(preset, objs);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<AuditEntry> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws DaoException {
        return isHqlPreset(preset) ?
                hqlPresetLookupDao.lookup(preset, objs, pagingInfo) : presetLookupDao.lookup(preset, objs, pagingInfo);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public int lookupCount(String preset, Object[] objs) throws DaoException {
        return isHqlPreset(preset) ?
                hqlPresetLookupDao.lookupCount(preset, objs) : presetLookupDao.lookupCount(preset, objs);
    }

    private boolean isHqlPreset(String preset) {
        return Objects.equals(preset, AuditEntryMaintainService.COMPOSITE_LOOKUP) ||
                Objects.equals(preset, AuditEntryMaintainService.GROUPED_LOOKUP);
    }
}
