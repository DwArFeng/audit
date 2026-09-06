package com.dwarfeng.audit.stack.handler;

import com.dwarfeng.audit.stack.bean.dto.*;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 自动审计任务操作处理器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionTaskOperateHandler extends Handler {

    InspectionTaskCreateResult create(InspectionTaskCreateInfo info) throws HandlerException;

    void start(InspectionTaskStartInfo info) throws HandlerException;

    void finish(InspectionTaskFinishInfo info) throws HandlerException;

    void fail(InspectionTaskFailInfo info) throws HandlerException;

    void expire(InspectionTaskExpireInfo info) throws HandlerException;

    void die(InspectionTaskDieInfo info) throws HandlerException;

    void updateModal(InspectionTaskUpdateModalInfo info) throws HandlerException;

    void beat(InspectionTaskBeatInfo info) throws HandlerException;
}
