package com.dwarfeng.audit.stack.exception;

/**
 * 接收器执行异常。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionReceiverExecutionException extends InspectionReceiverException {

    private static final long serialVersionUID = 4601458852252910373L;

    public InspectionReceiverExecutionException() {
    }

    public InspectionReceiverExecutionException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "自动审计接收器执行异常";
    }
}
