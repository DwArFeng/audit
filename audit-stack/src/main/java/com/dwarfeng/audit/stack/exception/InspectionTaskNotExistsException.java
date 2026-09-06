package com.dwarfeng.audit.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 自动审计任务不存在异常。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionTaskNotExistsException extends HandlerException {

    private static final long serialVersionUID = 260354954109884776L;

    private final LongIdKey inspectionTaskKey;

    public InspectionTaskNotExistsException(LongIdKey inspectionTaskKey) {
        this.inspectionTaskKey = inspectionTaskKey;
    }

    public InspectionTaskNotExistsException(Throwable cause, LongIdKey inspectionTaskKey) {
        super(cause);
        this.inspectionTaskKey = inspectionTaskKey;
    }

    @Override
    public String getMessage() {
        return "自动审计任务 " + inspectionTaskKey + " 不存在";
    }
}
