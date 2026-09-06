package com.dwarfeng.audit.stack.handler;

import com.dwarfeng.audit.stack.bean.dto.InspectionTaskEventCreateInfo;
import com.dwarfeng.audit.stack.bean.dto.InspectionTaskEventCreateResult;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 自动审计任务事件操作处理器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionTaskEventOperateHandler extends Handler {

    InspectionTaskEventCreateResult create(InspectionTaskEventCreateInfo info) throws HandlerException;
}
