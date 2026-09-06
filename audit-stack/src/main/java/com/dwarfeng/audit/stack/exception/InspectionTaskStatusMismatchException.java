package com.dwarfeng.audit.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.Set;

/**
 * 自动审计任务状态不匹配异常。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionTaskStatusMismatchException extends HandlerException {

    private static final long serialVersionUID = 5962486189880497534L;

    private final Set<Integer> validStatusSet;
    private final int actualStatus;

    public InspectionTaskStatusMismatchException(Set<Integer> validStatusSet, int actualStatus) {
        this.validStatusSet = validStatusSet;
        this.actualStatus = actualStatus;
    }

    public InspectionTaskStatusMismatchException(Throwable cause, Set<Integer> validStatusSet, int actualStatus) {
        super(cause);
        this.validStatusSet = validStatusSet;
        this.actualStatus = actualStatus;
    }

    @Override
    public String getMessage() {
        return "自动审计任务状态不匹配, 有效状态: " + validStatusSet + ", 实际状态: " + actualStatus;
    }
}
