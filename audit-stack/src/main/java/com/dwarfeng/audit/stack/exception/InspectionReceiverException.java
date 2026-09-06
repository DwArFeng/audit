package com.dwarfeng.audit.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 接收器异常。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionReceiverException extends HandlerException {

    private static final long serialVersionUID = -5866464123533711340L;

    public InspectionReceiverException() {
    }

    public InspectionReceiverException(String message, Throwable cause) {
        super(message, cause);
    }

    public InspectionReceiverException(String message) {
        super(message);
    }

    public InspectionReceiverException(Throwable cause) {
        super(cause);
    }
}
