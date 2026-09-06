package com.dwarfeng.audit.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 无效的自动审计任务状态异常。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InvalidInspectionTaskStatusException extends HandlerException {

    private static final long serialVersionUID = -8533338916755293529L;

    private final int status;

    public InvalidInspectionTaskStatusException(int status) {
        this.status = status;
    }

    public InvalidInspectionTaskStatusException(Throwable cause, int status) {
        super(cause);
        this.status = status;
    }

    @Override
    public String getMessage() {
        return "无效的自动审计任务状态: " + status;
    }
}
