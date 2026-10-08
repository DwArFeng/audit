package com.dwarfeng.audit.impl.service.operation;

import com.dwarfeng.audit.stack.bean.entity.InspectionAlarm;
import com.dwarfeng.audit.stack.bean.entity.InspectorInfo;
import com.dwarfeng.audit.stack.bean.entity.InspectorVariable;
import com.dwarfeng.audit.stack.bean.key.InspectorVariableKey;
import com.dwarfeng.audit.stack.cache.InspectionAlarmCache;
import com.dwarfeng.audit.stack.cache.InspectorInfoCache;
import com.dwarfeng.audit.stack.cache.InspectorVariableCache;
import com.dwarfeng.audit.stack.dao.InspectionAlarmDao;
import com.dwarfeng.audit.stack.dao.InspectorInfoDao;
import com.dwarfeng.audit.stack.dao.InspectorVariableDao;
import com.dwarfeng.audit.stack.service.InspectionAlarmMaintainService;
import com.dwarfeng.audit.stack.service.InspectorVariableMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InspectorInfoCrudOperation implements BatchCrudOperation<LongIdKey, InspectorInfo> {

    private final InspectorInfoDao inspectorInfoDao;
    private final InspectorInfoCache inspectorInfoCache;

    private final InspectorVariableDao inspectorVariableDao;
    private final InspectorVariableCache inspectorVariableCache;

    private final InspectionAlarmDao inspectionAlarmDao;
    private final InspectionAlarmCache inspectionAlarmCache;

    @Value("${com.dwarfeng.audit.cache.timeout.entity.inspector_info}")
    private long inspectorInfoTimeout;

    public InspectorInfoCrudOperation(
            InspectorInfoDao inspectorInfoDao,
            InspectorInfoCache inspectorInfoCache,
            InspectorVariableDao inspectorVariableDao,
            InspectorVariableCache inspectorVariableCache,
            InspectionAlarmDao inspectionAlarmDao,
            InspectionAlarmCache inspectionAlarmCache
    ) {
        this.inspectorInfoDao = inspectorInfoDao;
        this.inspectorInfoCache = inspectorInfoCache;
        this.inspectorVariableDao = inspectorVariableDao;
        this.inspectorVariableCache = inspectorVariableCache;
        this.inspectionAlarmDao = inspectionAlarmDao;
        this.inspectionAlarmCache = inspectionAlarmCache;
    }

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return inspectorInfoCache.exists(key) || inspectorInfoDao.exists(key);
    }

    @Override
    public InspectorInfo get(LongIdKey key) throws Exception {
        if (inspectorInfoCache.exists(key)) {
            return inspectorInfoCache.get(key);
        } else {
            if (!inspectorInfoDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            InspectorInfo inspectorInfo = inspectorInfoDao.get(key);
            inspectorInfoCache.push(inspectorInfo, inspectorInfoTimeout);
            return inspectorInfo;
        }
    }

    @Override
    public LongIdKey insert(InspectorInfo inspectorInfo) throws Exception {
        inspectorInfoCache.push(inspectorInfo, inspectorInfoTimeout);
        return inspectorInfoDao.insert(inspectorInfo);
    }

    @Override
    public void update(InspectorInfo inspectorInfo) throws Exception {
        inspectorInfoCache.push(inspectorInfo, inspectorInfoTimeout);
        inspectorInfoDao.update(inspectorInfo);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        // 删除与审计器信息相关的审计器变量。
        List<InspectorVariableKey> inspectorVariableKeys = inspectorVariableDao.lookup(
                InspectorVariableMaintainService.CHILD_FOR_INSPECTOR_INFO, new Object[]{key}
        ).stream().map(InspectorVariable::getKey).collect(Collectors.toList());
        inspectorVariableDao.batchDelete(inspectorVariableKeys);
        inspectorVariableCache.batchDelete(inspectorVariableKeys);

        // 解除与自动审计报警的关联。
        List<InspectionAlarm> inspectionAlarms = inspectionAlarmDao.lookup(
                InspectionAlarmMaintainService.CHILD_FOR_INSPECTOR_INFO, new Object[]{key}
        );
        inspectionAlarms.forEach(inspectionAlarm -> inspectionAlarm.setInspectorInfoKey(null));
        inspectionAlarmDao.batchUpdate(inspectionAlarms);
        inspectionAlarmCache.batchDelete(
                inspectionAlarms.stream().map(InspectionAlarm::getKey).collect(Collectors.toList())
        );

        // 删除审计器信息自身。
        inspectorInfoDao.delete(key);
        inspectorInfoCache.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return inspectorInfoCache.allExists(keys) || inspectorInfoDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return inspectorInfoCache.nonExists(keys) && inspectorInfoDao.nonExists(keys);
    }

    @Override
    public List<InspectorInfo> batchGet(List<LongIdKey> keys) throws Exception {
        if (inspectorInfoCache.allExists(keys)) {
            return inspectorInfoCache.batchGet(keys);
        } else {
            if (!inspectorInfoDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<InspectorInfo> inspectorInfos = inspectorInfoDao.batchGet(keys);
            inspectorInfoCache.batchPush(inspectorInfos, inspectorInfoTimeout);
            return inspectorInfos;
        }
    }

    @Override
    public List<LongIdKey> batchInsert(List<InspectorInfo> inspectorInfos) throws Exception {
        inspectorInfoCache.batchPush(inspectorInfos, inspectorInfoTimeout);
        return inspectorInfoDao.batchInsert(inspectorInfos);
    }

    @Override
    public void batchUpdate(List<InspectorInfo> inspectorInfos) throws Exception {
        inspectorInfoCache.batchPush(inspectorInfos, inspectorInfoTimeout);
        inspectorInfoDao.batchUpdate(inspectorInfos);
    }

    @Override
    public void batchDelete(List<LongIdKey> keys) throws Exception {
        for (LongIdKey key : keys) {
            delete(key);
        }
    }
}
