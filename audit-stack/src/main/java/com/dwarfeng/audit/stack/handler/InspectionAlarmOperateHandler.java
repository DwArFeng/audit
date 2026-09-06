package com.dwarfeng.audit.stack.handler;

import com.dwarfeng.audit.stack.bean.dto.InspectionAlarmCreateInfo;
import com.dwarfeng.audit.stack.bean.dto.InspectionAlarmCreateResult;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 自动审计报警操作处理器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionAlarmOperateHandler extends Handler {

    /**
     * 创建自动审计报警。
     *
     * @param info 自动审计报警创建信息。
     * @return 自动审计报警创建结果。
     * @throws HandlerException 处理器异常。
     */
    InspectionAlarmCreateResult create(InspectionAlarmCreateInfo info) throws HandlerException;
}
