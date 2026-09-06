package com.dwarfeng.audit.stack.exception;

/**
 * 审计器执行异常。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectorExecutionException extends InspectorException {

    private static final long serialVersionUID = 1200307032276129468L;

    public InspectorExecutionException() {
    }

    public InspectorExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InspectorExecutionException(String message) {
        super(message);
    }

    public InspectorExecutionException(Throwable cause) {
        super(cause);
    }
}
