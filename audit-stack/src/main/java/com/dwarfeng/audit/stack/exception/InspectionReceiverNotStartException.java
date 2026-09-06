package com.dwarfeng.audit.stack.exception;

/**
 * 接收器未启动异常。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionReceiverNotStartException extends InspectionReceiverException {

    private static final long serialVersionUID = 3570564402447587458L;

    public InspectionReceiverNotStartException() {
    }

    public InspectionReceiverNotStartException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "自动审计接收器未启动";
    }
}
