package com.dwarfeng.audit.impl.service.operation;

import com.dwarfeng.audit.stack.bean.entity.InspectionAlarm;
import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.audit.stack.bean.entity.InspectionTaskEvent;
import com.dwarfeng.audit.stack.cache.InspectionAlarmCache;
import com.dwarfeng.audit.stack.cache.InspectionTaskCache;
import com.dwarfeng.audit.stack.cache.InspectionTaskEventCache;
import com.dwarfeng.audit.stack.dao.InspectionAlarmDao;
import com.dwarfeng.audit.stack.dao.InspectionTaskDao;
import com.dwarfeng.audit.stack.dao.InspectionTaskEventDao;
import com.dwarfeng.audit.stack.service.InspectionAlarmMaintainService;
import com.dwarfeng.audit.stack.service.InspectionTaskEventMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InspectionTaskCrudOperation implements BatchCrudOperation<LongIdKey, InspectionTask> {

    private final InspectionTaskDao inspectionTaskDao;
    private final InspectionTaskCache inspectionTaskCache;

    private final InspectionTaskEventDao inspectionTaskEventDao;
    private final InspectionTaskEventCache inspectionTaskEventCache;

    private final InspectionAlarmDao inspectionAlarmDao;
    private final InspectionAlarmCache inspectionAlarmCache;

    @Value("${cache.timeout.entity.inspection_task}")
    private long inspectionTaskTimeout;

    public InspectionTaskCrudOperation(
            InspectionTaskDao inspectionTaskDao,
            InspectionTaskCache inspectionTaskCache,
            InspectionTaskEventDao inspectionTaskEventDao,
            InspectionTaskEventCache inspectionTaskEventCache,
            InspectionAlarmDao inspectionAlarmDao,
            InspectionAlarmCache inspectionAlarmCache
    ) {
        this.inspectionTaskDao = inspectionTaskDao;
        this.inspectionTaskCache = inspectionTaskCache;
        this.inspectionTaskEventDao = inspectionTaskEventDao;
        this.inspectionTaskEventCache = inspectionTaskEventCache;
        this.inspectionAlarmDao = inspectionAlarmDao;
        this.inspectionAlarmCache = inspectionAlarmCache;
    }

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return inspectionTaskCache.exists(key) || inspectionTaskDao.exists(key);
    }

    @Override
    public InspectionTask get(LongIdKey key) throws Exception {
        if (inspectionTaskCache.exists(key)) {
            return inspectionTaskCache.get(key);
        } else {
            if (!inspectionTaskDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            InspectionTask inspectionTask = inspectionTaskDao.get(key);
            inspectionTaskCache.push(inspectionTask, inspectionTaskTimeout);
            return inspectionTask;
        }
    }

    @Override
    public LongIdKey insert(InspectionTask inspectionTask) throws Exception {
        inspectionTaskCache.push(inspectionTask, inspectionTaskTimeout);
        return inspectionTaskDao.insert(inspectionTask);
    }

    @Override
    public void update(InspectionTask inspectionTask) throws Exception {
        inspectionTaskCache.push(inspectionTask, inspectionTaskTimeout);
        inspectionTaskDao.update(inspectionTask);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        // 删除与自动审计任务相关的自动审计任务事件。
        List<LongIdKey> inspectionTaskEventKeys = inspectionTaskEventDao.lookup(
                InspectionTaskEventMaintainService.CHILD_FOR_INSPECTION_TASK, new Object[]{key}
        ).stream().map(InspectionTaskEvent::getKey).collect(Collectors.toList());
        inspectionTaskEventDao.batchDelete(inspectionTaskEventKeys);
        inspectionTaskEventCache.batchDelete(inspectionTaskEventKeys);

        // 解除与自动审计报警的关联。
        List<InspectionAlarm> inspectionAlarms = inspectionAlarmDao.lookup(
                InspectionAlarmMaintainService.CHILD_FOR_INSPECTION_TASK, new Object[]{key}
        );
        inspectionAlarms.forEach(inspectionAlarm -> inspectionAlarm.setInspectionTaskKey(null));
        inspectionAlarmDao.batchUpdate(inspectionAlarms);
        inspectionAlarmCache.batchDelete(
                inspectionAlarms.stream().map(InspectionAlarm::getKey).collect(Collectors.toList())
        );

        // 删除自动审计任务自身。
        inspectionTaskDao.delete(key);
        inspectionTaskCache.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return inspectionTaskCache.allExists(keys) || inspectionTaskDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return inspectionTaskCache.nonExists(keys) && inspectionTaskDao.nonExists(keys);
    }

    @Override
    public List<InspectionTask> batchGet(List<LongIdKey> keys) throws Exception {
        if (inspectionTaskCache.allExists(keys)) {
            return inspectionTaskCache.batchGet(keys);
        } else {
            if (!inspectionTaskDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<InspectionTask> inspectionTasks = inspectionTaskDao.batchGet(keys);
            inspectionTaskCache.batchPush(inspectionTasks, inspectionTaskTimeout);
            return inspectionTasks;
        }
    }

    @Override
    public List<LongIdKey> batchInsert(List<InspectionTask> inspectionTasks) throws Exception {
        inspectionTaskCache.batchPush(inspectionTasks, inspectionTaskTimeout);
        return inspectionTaskDao.batchInsert(inspectionTasks);
    }

    @Override
    public void batchUpdate(List<InspectionTask> inspectionTasks) throws Exception {
        inspectionTaskCache.batchPush(inspectionTasks, inspectionTaskTimeout);
        inspectionTaskDao.batchUpdate(inspectionTasks);
    }

    @Override
    public void batchDelete(List<LongIdKey> keys) throws Exception {
        for (LongIdKey key : keys) {
            delete(key);
        }
    }
}
