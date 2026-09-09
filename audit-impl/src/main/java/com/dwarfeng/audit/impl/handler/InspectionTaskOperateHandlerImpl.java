package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.sdk.util.Constants;
import com.dwarfeng.audit.stack.bean.dto.*;
import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.audit.stack.handler.InspectionTaskOperateHandler;
import com.dwarfeng.audit.stack.handler.PushHandler;
import com.dwarfeng.audit.stack.service.InspectionTaskMaintainService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.generation.KeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 自动审计任务操作处理器实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class InspectionTaskOperateHandlerImpl implements InspectionTaskOperateHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectionTaskOperateHandlerImpl.class);

    private static final Set<Integer> VALID_STATUS_SET_START;
    private static final Set<Integer> VALID_STATUS_SET_FINISH;
    private static final Set<Integer> VALID_STATUS_SET_FAIL;
    private static final Set<Integer> VALID_STATUS_SET_EXPIRE;
    private static final Set<Integer> VALID_STATUS_SET_DIE;
    private static final Set<Integer> VALID_STATUS_SET_UPDATE_MODAL;
    private static final Set<Integer> VALID_STATUS_SET_BEAT;

    static {
        VALID_STATUS_SET_START = Collections.singleton(Constants.INSPECTION_TASK_STATUS_CREATED);

        Set<Integer> finish = new HashSet<>();
        finish.add(Constants.INSPECTION_TASK_STATUS_CREATED);
        finish.add(Constants.INSPECTION_TASK_STATUS_PROCESSING);
        VALID_STATUS_SET_FINISH = Collections.unmodifiableSet(finish);

        Set<Integer> fail = new HashSet<>();
        fail.add(Constants.INSPECTION_TASK_STATUS_CREATED);
        fail.add(Constants.INSPECTION_TASK_STATUS_PROCESSING);
        VALID_STATUS_SET_FAIL = Collections.unmodifiableSet(fail);

        Set<Integer> expire = new HashSet<>();
        expire.add(Constants.INSPECTION_TASK_STATUS_CREATED);
        expire.add(Constants.INSPECTION_TASK_STATUS_PROCESSING);
        VALID_STATUS_SET_EXPIRE = Collections.unmodifiableSet(expire);

        VALID_STATUS_SET_DIE = Collections.singleton(Constants.INSPECTION_TASK_STATUS_PROCESSING);
        VALID_STATUS_SET_UPDATE_MODAL = Collections.singleton(Constants.INSPECTION_TASK_STATUS_PROCESSING);
        VALID_STATUS_SET_BEAT = Collections.singleton(Constants.INSPECTION_TASK_STATUS_PROCESSING);
    }

    private final InspectionTaskMaintainService inspectionTaskMaintainService;
    private final PushHandler pushHandler;
    private final KeyGenerator<LongIdKey> keyGenerator;
    private final HandlerValidator handlerValidator;

    @Value("${com.dwarfeng.audit.inspection_task.expire_timeout}")
    private long expireTimeout;
    @Value("${com.dwarfeng.audit.inspection_task.die_timeout}")
    private long dieTimeout;

    public InspectionTaskOperateHandlerImpl(
            InspectionTaskMaintainService inspectionTaskMaintainService,
            PushHandler pushHandler,
            KeyGenerator<LongIdKey> keyGenerator,
            HandlerValidator handlerValidator
    ) {
        this.inspectionTaskMaintainService = inspectionTaskMaintainService;
        this.pushHandler = pushHandler;
        this.keyGenerator = keyGenerator;
        this.handlerValidator = handlerValidator;
    }

    // 为了保证代码的可读性，此处代码不做简化。
    @SuppressWarnings("ExtractMethodRecommender")
    @BehaviorAnalyse
    @Override
    public InspectionTaskCreateResult create(InspectionTaskCreateInfo info) throws HandlerException {
        try {
            LongIdKey inspectionKey = info.getInspectionKey();
            handlerValidator.makeSureInspectionExists(inspectionKey);

            LongIdKey taskKey = keyGenerator.generate();
            Date createdDate = new Date();
            Date shouldExpireDate = expireTimeout <= 0
                    ? new Date(Long.MAX_VALUE)
                    : new Date(createdDate.getTime() + expireTimeout);
            InspectionTask task = new InspectionTask(
                    taskKey,
                    inspectionKey,
                    Constants.INSPECTION_TASK_STATUS_CREATED,
                    createdDate,
                    null,
                    null,
                    0L,
                    shouldExpireDate,
                    null,
                    null,
                    null,
                    null
            );
            inspectionTaskMaintainService.insert(task);
            return new InspectionTaskCreateResult(taskKey);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @BehaviorAnalyse
    @Override
    public void start(InspectionTaskStartInfo info) throws HandlerException {
        try {
            handlerValidator.makeSureInspectionTaskStatusValid(
                    info.getInspectionTaskKey(), VALID_STATUS_SET_START
            );
            InspectionTask task = inspectionTaskMaintainService.get(info.getInspectionTaskKey());
            Date currentDate = new Date();
            task.setStatus(Constants.INSPECTION_TASK_STATUS_PROCESSING);
            task.setStartedDate(currentDate);
            task.setShouldDieDate(new Date(currentDate.getTime() + Math.max(dieTimeout, 0L)));
            inspectionTaskMaintainService.update(task);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @BehaviorAnalyse
    @Override
    public void finish(InspectionTaskFinishInfo info) throws HandlerException {
        try {
            finish(info.getInspectionTaskKey());
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void finish(LongIdKey taskKey) throws Exception {
        handlerValidator.makeSureInspectionTaskStatusValid(taskKey, VALID_STATUS_SET_FINISH);
        InspectionTask task = inspectionTaskMaintainService.get(taskKey);
        Date currentDate = new Date();
        task.setStatus(Constants.INSPECTION_TASK_STATUS_FINISHED);
        task.setEndedDate(currentDate);
        task.setDuration(duration(task, currentDate));
        inspectionTaskMaintainService.update(task);
        pushInspectionTaskEvent(task, Constants.INSPECTION_TASK_STATUS_FINISHED);
    }

    @BehaviorAnalyse
    @Override
    public void fail(InspectionTaskFailInfo info) throws HandlerException {
        try {
            handlerValidator.makeSureInspectionTaskStatusValid(
                    info.getInspectionTaskKey(), VALID_STATUS_SET_FAIL
            );
            InspectionTask task = inspectionTaskMaintainService.get(info.getInspectionTaskKey());
            Date currentDate = new Date();
            task.setStatus(Constants.INSPECTION_TASK_STATUS_FAILED);
            task.setEndedDate(currentDate);
            task.setDuration(duration(task, currentDate));
            inspectionTaskMaintainService.update(task);
            pushInspectionTaskEvent(task, Constants.INSPECTION_TASK_STATUS_FAILED);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @BehaviorAnalyse
    @Override
    public void expire(InspectionTaskExpireInfo info) throws HandlerException {
        try {
            handlerValidator.makeSureInspectionTaskStatusValid(
                    info.getInspectionTaskKey(), VALID_STATUS_SET_EXPIRE
            );
            InspectionTask task = inspectionTaskMaintainService.get(info.getInspectionTaskKey());
            Date currentDate = new Date();
            task.setStatus(Constants.INSPECTION_TASK_STATUS_EXPIRED);
            task.setEndedDate(currentDate);
            task.setDuration(duration(task, currentDate));
            task.setExpiredDate(currentDate);
            inspectionTaskMaintainService.update(task);
            pushInspectionTaskEvent(task, Constants.INSPECTION_TASK_STATUS_EXPIRED);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @BehaviorAnalyse
    @Override
    public void die(InspectionTaskDieInfo info) throws HandlerException {
        try {
            handlerValidator.makeSureInspectionTaskStatusValid(
                    info.getInspectionTaskKey(), VALID_STATUS_SET_DIE
            );
            InspectionTask task = inspectionTaskMaintainService.get(info.getInspectionTaskKey());
            Date currentDate = new Date();
            task.setStatus(Constants.INSPECTION_TASK_STATUS_DIED);
            task.setEndedDate(currentDate);
            task.setDuration(duration(task, currentDate));
            task.setDiedDate(currentDate);
            inspectionTaskMaintainService.update(task);
            pushInspectionTaskEvent(task, Constants.INSPECTION_TASK_STATUS_DIED);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @BehaviorAnalyse
    @Override
    public void updateModal(InspectionTaskUpdateModalInfo info) throws HandlerException {
        try {
            handlerValidator.makeSureInspectionTaskStatusValid(
                    info.getInspectionTaskKey(), VALID_STATUS_SET_UPDATE_MODAL
            );
            InspectionTask task = inspectionTaskMaintainService.get(info.getInspectionTaskKey());
            task.setAnchorMessage(info.getAnchorMessage());
            inspectionTaskMaintainService.update(task);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @BehaviorAnalyse
    @Override
    public void beat(InspectionTaskBeatInfo info) throws HandlerException {
        try {
            handlerValidator.makeSureInspectionTaskStatusValid(
                    info.getInspectionTaskKey(), VALID_STATUS_SET_BEAT
            );
            InspectionTask task = inspectionTaskMaintainService.get(info.getInspectionTaskKey());
            task.setShouldDieDate(new Date(System.currentTimeMillis() + Math.max(dieTimeout, 0L)));
            inspectionTaskMaintainService.update(task);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private static Long duration(InspectionTask task, Date endedDate) {
        Date startDate = task.getStartedDate() == null ? task.getCreatedDate() : task.getStartedDate();
        return startDate == null ? null : endedDate.getTime() - startDate.getTime();
    }

    private void pushInspectionTaskEvent(InspectionTask inspectionTask, int status) {
        try {
            switch (status) {
                case Constants.INSPECTION_TASK_STATUS_FINISHED:
                    pushHandler.inspectionTaskFinished(inspectionTask);
                    break;
                case Constants.INSPECTION_TASK_STATUS_FAILED:
                    pushHandler.inspectionTaskFailed(inspectionTask);
                    break;
                case Constants.INSPECTION_TASK_STATUS_EXPIRED:
                    pushHandler.inspectionTaskExpired(inspectionTask);
                    break;
                case Constants.INSPECTION_TASK_STATUS_DIED:
                    pushHandler.inspectionTaskDied(inspectionTask);
                    break;
                default:
                    throw new IllegalArgumentException("未知的自动审计任务终结状态: " + status);
            }
        } catch (Exception e) {
            LOGGER.warn("推送自动审计任务终结消息时发生异常, 本次消息将不会被推送, 异常信息如下: ", e);
        }
    }
}
