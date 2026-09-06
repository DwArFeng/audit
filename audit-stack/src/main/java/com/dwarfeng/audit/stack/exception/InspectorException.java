package com.dwarfeng.audit.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 审计器异常。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectorException extends HandlerException {

    private static final long serialVersionUID = -595714740574210572L;

    public InspectorException() {
    }

    public InspectorException(String message, Throwable cause) {
        super(message, cause);
    }

    public InspectorException(String message) {
        super(message);
    }

    public InspectorException(Throwable cause) {
        super(cause);
    }
}
