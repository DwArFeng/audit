package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.stack.bean.dto.InspectionAlarmCreateInfo;
import com.dwarfeng.audit.stack.bean.dto.InspectionAlarmCreateResult;
import com.dwarfeng.audit.stack.bean.entity.InspectionAlarm;
import com.dwarfeng.audit.stack.handler.InspectionAlarmOperateHandler;
import com.dwarfeng.audit.stack.handler.PushHandler;
import com.dwarfeng.audit.stack.service.InspectionAlarmMaintainService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自动审计报警操作处理器实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class InspectionAlarmOperateHandlerImpl implements InspectionAlarmOperateHandler {

    private final InspectionAlarmMaintainService inspectionAlarmMaintainService;
    private final PushHandler pushHandler;
    private final HandlerValidator handlerValidator;

    public InspectionAlarmOperateHandlerImpl(
            InspectionAlarmMaintainService inspectionAlarmMaintainService,
            PushHandler pushHandler,
            HandlerValidator handlerValidator
    ) {
        this.inspectionAlarmMaintainService = inspectionAlarmMaintainService;
        this.pushHandler = pushHandler;
        this.handlerValidator = handlerValidator;
    }

    @BehaviorAnalyse
    @Override
    public InspectionAlarmCreateResult create(InspectionAlarmCreateInfo info) throws HandlerException {
        try {
            return create0(info);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private InspectionAlarmCreateResult create0(InspectionAlarmCreateInfo info) throws Exception {
        // 展开参数。
        LongIdKey inspectionKey = info.getInspectionKey();
        LongIdKey inspectionTaskKey = info.getInspectionTaskKey();
        LongIdKey inspectorInfoKey = info.getInspectorInfoKey();
        String type = info.getType();
        String message = info.getMessage();

        // 确认自动审计、自动审计任务、审计器信息存在。
        handlerValidator.makeSureInspectionExists(inspectionKey);
        handlerValidator.makeSureInspectionTaskExists(inspectionTaskKey);
        handlerValidator.makeSureInspectorInfoExists(inspectorInfoKey);
        // 确认自动审计任务、审计器信息归属于指定的自动审计。
        handlerValidator.makeSureInspectionTaskInspectionMatched(inspectionTaskKey, inspectionKey);
        handlerValidator.makeSureInspectorInfoInspectionMatched(inspectorInfoKey, inspectionKey);

        // 构建自动审计报警。
        InspectionAlarm inspectionAlarm = new InspectionAlarm(
                null, inspectionKey, inspectionTaskKey, inspectorInfoKey, new Date(), type, message
        );

        // 调用维护服务插入自动审计报警。
        LongIdKey inspectionAlarmKey = inspectionAlarmMaintainService.insertOrUpdate(inspectionAlarm);
        inspectionAlarm.setKey(inspectionAlarmKey);

        // 推送自动审计报警创建事件。
        pushHandler.inspectionAlarmCreated(inspectionAlarm);

        // 构建返回值并返回。
        return new InspectionAlarmCreateResult(inspectionAlarmKey);
    }
}
