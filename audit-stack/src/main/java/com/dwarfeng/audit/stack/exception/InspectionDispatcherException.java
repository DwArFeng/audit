package com.dwarfeng.audit.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 调度器异常。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionDispatcherException extends HandlerException {

    private static final long serialVersionUID = 9058010423586083312L;

    public InspectionDispatcherException() {
    }

    public InspectionDispatcherException(String message, Throwable cause) {
        super(message, cause);
    }

    public InspectionDispatcherException(String message) {
        super(message);
    }

    public InspectionDispatcherException(Throwable cause) {
        super(cause);
    }
}
