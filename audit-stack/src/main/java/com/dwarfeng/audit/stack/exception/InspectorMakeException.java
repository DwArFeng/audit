package com.dwarfeng.audit.stack.exception;

/**
 * 审计器构造异常。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectorMakeException extends InspectorException {

    private static final long serialVersionUID = -6798027507405977706L;

    public InspectorMakeException() {
    }

    public InspectorMakeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InspectorMakeException(String message) {
        super(message);
    }

    public InspectorMakeException(Throwable cause) {
        super(cause);
    }
}
