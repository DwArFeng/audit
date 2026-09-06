package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.stack.bean.dto.InspectionTaskEventCreateInfo;
import com.dwarfeng.audit.stack.bean.dto.InspectionTaskEventCreateResult;
import com.dwarfeng.audit.stack.bean.entity.InspectionTaskEvent;
import com.dwarfeng.audit.stack.handler.InspectionTaskEventOperateHandler;
import com.dwarfeng.audit.stack.service.InspectionTaskEventMaintainService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.generation.KeyGenerator;
import org.springframework.stereotype.Component;

/**
 * 自动审计任务事件操作处理器实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class InspectionTaskEventOperateHandlerImpl implements InspectionTaskEventOperateHandler {

    private final InspectionTaskEventMaintainService inspectionTaskEventMaintainService;
    private final HandlerValidator handlerValidator;
    private final KeyGenerator<LongIdKey> keyGenerator;

    public InspectionTaskEventOperateHandlerImpl(
            InspectionTaskEventMaintainService inspectionTaskEventMaintainService,
            HandlerValidator handlerValidator,
            KeyGenerator<LongIdKey> keyGenerator
    ) {
        this.inspectionTaskEventMaintainService = inspectionTaskEventMaintainService;
        this.handlerValidator = handlerValidator;
        this.keyGenerator = keyGenerator;
    }

    @BehaviorAnalyse
    @Override
    public InspectionTaskEventCreateResult create(InspectionTaskEventCreateInfo info) throws HandlerException {
        try {
            handlerValidator.makeSureInspectionTaskExists(info.getInspectionTaskKey());
            LongIdKey eventKey = keyGenerator.generate();
            InspectionTaskEvent event = new InspectionTaskEvent(
                    eventKey,
                    info.getInspectionTaskKey(),
                    info.getHappenedDate(),
                    info.getMessage()
            );
            inspectionTaskEventMaintainService.insert(event);
            return new InspectionTaskEventCreateResult(eventKey);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }
}
