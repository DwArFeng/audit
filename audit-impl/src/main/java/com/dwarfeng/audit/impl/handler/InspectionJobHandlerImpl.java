package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.stack.bean.dto.*;
import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.audit.stack.bean.entity.InspectorInfo;
import com.dwarfeng.audit.stack.handler.*;
import com.dwarfeng.audit.stack.service.InspectionTaskMaintainService;
import com.dwarfeng.audit.stack.struct.InspectionJobLocalCache;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自动审计作业处理器实现。
 *
 * <p>
 * 作业处理器负责将任务生命周期与审计器执行串联起来，并使用任务心跳维持执行租约。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class InspectionJobHandlerImpl implements InspectionJobHandler {

    private final InspectionTaskMaintainService inspectionTaskMaintainService;
    private final InspectionJobLocalCacheHandler inspectionJobLocalCacheHandler;

    private final InspectionTaskOperateHandler inspectionTaskOperateHandler;
    private final InspectionTaskEventOperateHandler inspectionTaskEventOperateHandler;
    private final AuditEntryLookupHandler auditEntryLookupHandler;
    private final InspectionAlarmOperateHandler inspectionAlarmOperateHandler;
    private final InspectorVariableOperateHandler inspectorVariableOperateHandler;

    private final ThreadPoolTaskExecutor executor;
    private final ThreadPoolTaskScheduler scheduler;

    @Value("${com.dwarfeng.audit.inspection_task.beat_interval}")
    private long beatInterval;

    private final ConcurrentMap<LongIdKey, Lock> executeLocks = new ConcurrentHashMap<>();

    public InspectionJobHandlerImpl(
            InspectionTaskMaintainService inspectionTaskMaintainService,
            InspectionJobLocalCacheHandler inspectionJobLocalCacheHandler,
            InspectionTaskOperateHandler inspectionTaskOperateHandler,
            InspectionTaskEventOperateHandler inspectionTaskEventOperateHandler,
            AuditEntryLookupHandler auditEntryLookupHandler,
            InspectionAlarmOperateHandler inspectionAlarmOperateHandler,
            InspectorVariableOperateHandler inspectorVariableOperateHandler,
            ThreadPoolTaskExecutor executor,
            ThreadPoolTaskScheduler scheduler
    ) {
        this.inspectionTaskMaintainService = inspectionTaskMaintainService;
        this.inspectionJobLocalCacheHandler = inspectionJobLocalCacheHandler;
        this.inspectionTaskOperateHandler = inspectionTaskOperateHandler;
        this.inspectionTaskEventOperateHandler = inspectionTaskEventOperateHandler;
        this.auditEntryLookupHandler = auditEntryLookupHandler;
        this.inspectionAlarmOperateHandler = inspectionAlarmOperateHandler;
        this.inspectorVariableOperateHandler = inspectorVariableOperateHandler;
        this.executor = executor;
        this.scheduler = scheduler;
    }

    @BehaviorAnalyse
    @Override
    public InspectionJobCreateResult create(InspectionJobCreateInfo info) throws HandlerException {
        InspectionTaskCreateResult result = inspectionTaskOperateHandler.create(
                new InspectionTaskCreateInfo(info.getInspectionKey())
        );
        return new InspectionJobCreateResult(result.getInspectionTaskKey());
    }

    @BehaviorAnalyse
    @Override
    public void execute(InspectionJobExecuteInfo info) throws HandlerException {
        Objects.requireNonNull(info, "自动审计作业执行信息不能为 null");
        LongIdKey taskKey = info.getInspectionTaskKey();
        Objects.requireNonNull(taskKey, "自动审计任务主键不能为 null");

        Lock executeLock = executeLocks.computeIfAbsent(taskKey, ignored -> new ReentrantLock());
        executeLock.lock();
        try {
            execute0(taskKey);
        } finally {
            executeLock.unlock();
        }
    }

    private void execute0(LongIdKey taskKey) throws HandlerException {
        InspectionTask task = getTask(taskKey);
        if (task.getStatus() != com.dwarfeng.audit.sdk.util.Constants.INSPECTION_TASK_STATUS_CREATED) {
            return;
        }
        if (isExpired(task)) {
            inspectionTaskOperateHandler.expire(new InspectionTaskExpireInfo(taskKey));
            createEvent(taskKey, "任务在启动前超过最终截止时间。");
            return;
        }

        InspectionJobLocalCache cache;
        try {
            cache = inspectionJobLocalCacheHandler.get(task.getInspectionKey());
        } catch (Exception e) {
            failIfActive(taskKey, "审计执行配置加载失败: " + messageOf(e));
            return;
        }
        try {
            inspectionTaskOperateHandler.start(new InspectionTaskStartInfo(taskKey));
        } catch (Exception e) {
            failIfActive(taskKey, "任务启动失败: " + messageOf(e));
            return;
        }
        createEvent(taskKey, "任务已开始执行。");

        ScheduledFuture<?> heartbeatFuture = scheduleHeartbeat(taskKey);
        try {
            for (InspectorInfo inspectorInfo : cache.getInspectorInfos()) {
                if (!inspectorInfo.isEnabled() || !continueProcessing(taskKey)) {
                    continue;
                }
                Inspector inspector = cache.getInspectorMap().get(inspectorInfo.getKey());
                Inspector.Executor inspectorExecutor = inspector.newExecutor();
                inspectorExecutor.init(new InspectorContext(cache.getInspection(), getTask(taskKey), inspectorInfo));
                inspectorExecutor.inspect();
            }
            if (continueProcessing(taskKey)) {
                inspectionTaskOperateHandler.finish(new InspectionTaskFinishInfo(taskKey));
                createEvent(taskKey, "任务执行完成。");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            failIfActive(taskKey, "任务执行被中断。");
        } catch (Exception e) {
            failIfActive(taskKey, "任务执行失败: " + messageOf(e));
        } finally {
            heartbeatFuture.cancel(false);
        }
    }

    @Override
    public CompletableFuture<Void> executeAsync(InspectionJobExecuteInfo info) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        executor.execute(() -> {
            try {
                execute(info);
                future.complete(null);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
        return future;
    }

    private ScheduledFuture<?> scheduleHeartbeat(LongIdKey taskKey) {
        return scheduler.scheduleAtFixedRate(() -> {
            try {
                InspectionTask task = getTask(taskKey);
                if (task.getStatus() == com.dwarfeng.audit.sdk.util.Constants.INSPECTION_TASK_STATUS_PROCESSING) {
                    inspectionTaskOperateHandler.beat(new InspectionTaskBeatInfo(taskKey));
                }
            } catch (Exception ignored) {
                // 本次心跳更新失败，下一周期继续尝试。
            }
        }, beatInterval);
    }

    private boolean continueProcessing(LongIdKey taskKey) throws HandlerException {
        InspectionTask task = getTask(taskKey);
        if (task.getStatus() != com.dwarfeng.audit.sdk.util.Constants.INSPECTION_TASK_STATUS_PROCESSING) {
            return false;
        }
        if (isExpired(task)) {
            inspectionTaskOperateHandler.expire(new InspectionTaskExpireInfo(taskKey));
            createEvent(taskKey, "任务执行超过最终截止时间。");
            return false;
        }
        return true;
    }

    private void failIfActive(LongIdKey taskKey, String message) throws HandlerException {
        InspectionTask task = getTask(taskKey);
        int status = task.getStatus();
        if (status != com.dwarfeng.audit.sdk.util.Constants.INSPECTION_TASK_STATUS_CREATED &&
                status != com.dwarfeng.audit.sdk.util.Constants.INSPECTION_TASK_STATUS_PROCESSING) {
            return;
        }
        inspectionTaskOperateHandler.fail(new InspectionTaskFailInfo(taskKey));
        createEvent(taskKey, message);
    }

    private InspectionTask getTask(LongIdKey taskKey) throws HandlerException {
        try {
            if (taskKey == null || !inspectionTaskMaintainService.exists(taskKey)) {
                throw new IllegalArgumentException("自动审计任务不存在: " + taskKey);
            }
            return inspectionTaskMaintainService.get(taskKey);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void createEvent(LongIdKey taskKey, String message) throws HandlerException {
        inspectionTaskEventOperateHandler.create(
                new InspectionTaskEventCreateInfo(taskKey, new Date(), message)
        );
    }

    private static boolean isExpired(InspectionTask task) {
        return task.getShouldExpireDate() != null && !task.getShouldExpireDate().after(new Date());
    }

    private static String messageOf(Exception e) {
        String message = e.getMessage();
        return message == null || message.trim().isEmpty() ? e.getClass().getSimpleName() : message;
    }

    private class InspectorContext implements Inspector.Context {

        private final Inspection inspection;
        private final InspectionTask inspectionTask;
        private final InspectorInfo inspectorInfo;

        private InspectorContext(Inspection inspection, InspectionTask inspectionTask, InspectorInfo inspectorInfo) {
            this.inspection = inspection;
            this.inspectionTask = inspectionTask;
            this.inspectorInfo = inspectorInfo;
        }

        @Override
        public Inspection getInspection() {
            return inspection;
        }

        @Override
        public InspectionTask getInspectionTask() {
            return inspectionTask;
        }

        @Override
        public InspectorInfo getInspectorInfo() {
            return inspectorInfo;
        }

        @Override
        public AuditEntryLookupResult lookupComposite(AuditEntryCompositeLookupInfo info) throws Exception {
            return auditEntryLookupHandler.lookupComposite(info);
        }

        @Override
        public AuditEntryLookupResult lookupGrouped(AuditEntryGroupedLookupInfo info) throws Exception {
            return auditEntryLookupHandler.lookupGrouped(info);
        }

        @Override
        public InspectionAlarmCreateResult createInspectionAlarm(InspectionAlarmCreateInfo info) throws Exception {
            return inspectionAlarmOperateHandler.create(info);
        }

        @Override
        public InspectorVariableInspectResult inspectInspectorVariable(InspectorVariableInspectInfo info)
                throws Exception {
            return inspectorVariableOperateHandler.inspect(info);
        }

        @Override
        public void upsertInspectorVariable(InspectorVariableUpsertInfo info) throws Exception {
            inspectorVariableOperateHandler.upsert(info);
        }

        @Override
        public void removeInspectorVariable(InspectorVariableRemoveInfo info) throws Exception {
            inspectorVariableOperateHandler.remove(info);
        }

        @Override
        public void updateInspectorTaskModal(InspectionTaskUpdateModalInfo info) throws Exception {
            inspectionTaskOperateHandler.updateModal(info);
        }

        @Override
        public void createInspectorTaskEvent(InspectionTaskEventCreateInfo info) throws Exception {
            inspectionTaskEventOperateHandler.create(info);
        }
    }
}
