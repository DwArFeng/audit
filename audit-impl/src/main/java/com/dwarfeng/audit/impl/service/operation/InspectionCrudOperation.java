package com.dwarfeng.audit.impl.service.operation;

import com.dwarfeng.audit.stack.bean.entity.*;
import com.dwarfeng.audit.stack.cache.InspectionAlarmCache;
import com.dwarfeng.audit.stack.cache.InspectionCache;
import com.dwarfeng.audit.stack.cache.InspectionDriverInfoCache;
import com.dwarfeng.audit.stack.dao.*;
import com.dwarfeng.audit.stack.service.InspectionAlarmMaintainService;
import com.dwarfeng.audit.stack.service.InspectionDriverInfoMaintainService;
import com.dwarfeng.audit.stack.service.InspectionTaskMaintainService;
import com.dwarfeng.audit.stack.service.InspectorInfoMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InspectionCrudOperation implements BatchCrudOperation<LongIdKey, Inspection> {

    private final InspectionDao inspectionDao;
    private final InspectionCache inspectionCache;

    private final InspectionDriverInfoDao inspectionDriverInfoDao;
    private final InspectionDriverInfoCache inspectionDriverInfoCache;

    private final InspectionTaskDao inspectionTaskDao;
    private final InspectionTaskCrudOperation inspectionTaskCrudOperation;

    private final InspectorInfoDao inspectorInfoDao;
    private final InspectorInfoCrudOperation inspectorInfoCrudOperation;

    private final InspectionAlarmDao inspectionAlarmDao;
    private final InspectionAlarmCache inspectionAlarmCache;

    @Value("${com.dwarfeng.audit.cache.timeout.entity.inspection}")
    private long inspectionTimeout;

    public InspectionCrudOperation(
            InspectionDao inspectionDao,
            InspectionCache inspectionCache,
            InspectionDriverInfoDao inspectionDriverInfoDao,
            InspectionDriverInfoCache inspectionDriverInfoCache,
            InspectionTaskDao inspectionTaskDao,
            InspectionTaskCrudOperation inspectionTaskCrudOperation,
            InspectorInfoDao inspectorInfoDao,
            InspectorInfoCrudOperation inspectorInfoCrudOperation,
            InspectionAlarmDao inspectionAlarmDao,
            InspectionAlarmCache inspectionAlarmCache
    ) {
        this.inspectionDao = inspectionDao;
        this.inspectionCache = inspectionCache;
        this.inspectionDriverInfoDao = inspectionDriverInfoDao;
        this.inspectionDriverInfoCache = inspectionDriverInfoCache;
        this.inspectionTaskDao = inspectionTaskDao;
        this.inspectionTaskCrudOperation = inspectionTaskCrudOperation;
        this.inspectorInfoDao = inspectorInfoDao;
        this.inspectorInfoCrudOperation = inspectorInfoCrudOperation;
        this.inspectionAlarmDao = inspectionAlarmDao;
        this.inspectionAlarmCache = inspectionAlarmCache;
    }

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return inspectionCache.exists(key) || inspectionDao.exists(key);
    }

    @Override
    public Inspection get(LongIdKey key) throws Exception {
        if (inspectionCache.exists(key)) {
            return inspectionCache.get(key);
        } else {
            if (!inspectionDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            Inspection inspection = inspectionDao.get(key);
            inspectionCache.push(inspection, inspectionTimeout);
            return inspection;
        }
    }

    @Override
    public LongIdKey insert(Inspection inspection) throws Exception {
        inspectionCache.push(inspection, inspectionTimeout);
        return inspectionDao.insert(inspection);
    }

    @Override
    public void update(Inspection inspection) throws Exception {
        inspectionCache.push(inspection, inspectionTimeout);
        inspectionDao.update(inspection);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        // 删除与自动审计相关的自动审计驱动器信息。
        List<LongIdKey> inspectionDriverInfoKeys = inspectionDriverInfoDao.lookup(
                InspectionDriverInfoMaintainService.CHILD_FOR_INSPECTION, new Object[]{key}
        ).stream().map(InspectionDriverInfo::getKey).collect(Collectors.toList());
        inspectionDriverInfoDao.batchDelete(inspectionDriverInfoKeys);
        inspectionDriverInfoCache.batchDelete(inspectionDriverInfoKeys);

        // 删除与自动审计相关的自动审计任务。
        List<LongIdKey> inspectionTaskKeys = inspectionTaskDao.lookup(
                InspectionTaskMaintainService.CHILD_FOR_INSPECTION, new Object[]{key}
        ).stream().map(InspectionTask::getKey).collect(Collectors.toList());
        inspectionTaskCrudOperation.batchDelete(inspectionTaskKeys);

        // 删除与自动审计相关的审计器信息。
        List<LongIdKey> inspectorInfoKeys = inspectorInfoDao.lookup(
                InspectorInfoMaintainService.CHILD_FOR_INSPECTION, new Object[]{key}
        ).stream().map(InspectorInfo::getKey).collect(Collectors.toList());
        inspectorInfoCrudOperation.batchDelete(inspectorInfoKeys);

        // 解除与自动审计报警的关联。
        List<InspectionAlarm> inspectionAlarms = inspectionAlarmDao.lookup(
                InspectionAlarmMaintainService.CHILD_FOR_INSPECTION, new Object[]{key}
        );
        inspectionAlarms.forEach(inspectionAlarm -> inspectionAlarm.setInspectionKey(null));
        inspectionAlarmDao.batchUpdate(inspectionAlarms);
        inspectionAlarmCache.batchDelete(
                inspectionAlarms.stream().map(InspectionAlarm::getKey).collect(Collectors.toList())
        );

        // 删除自动审计自身。
        inspectionDao.delete(key);
        inspectionCache.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return inspectionCache.allExists(keys) || inspectionDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return inspectionCache.nonExists(keys) && inspectionDao.nonExists(keys);
    }

    @Override
    public List<Inspection> batchGet(List<LongIdKey> keys) throws Exception {
        if (inspectionCache.allExists(keys)) {
            return inspectionCache.batchGet(keys);
        } else {
            if (!inspectionDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<Inspection> inspections = inspectionDao.batchGet(keys);
            inspectionCache.batchPush(inspections, inspectionTimeout);
            return inspections;
        }
    }

    @Override
    public List<LongIdKey> batchInsert(List<Inspection> inspections) throws Exception {
        inspectionCache.batchPush(inspections, inspectionTimeout);
        return inspectionDao.batchInsert(inspections);
    }

    @Override
    public void batchUpdate(List<Inspection> inspections) throws Exception {
        inspectionCache.batchPush(inspections, inspectionTimeout);
        inspectionDao.batchUpdate(inspections);
    }

    @Override
    public void batchDelete(List<LongIdKey> keys) throws Exception {
        for (LongIdKey key : keys) {
            delete(key);
        }
    }
}
