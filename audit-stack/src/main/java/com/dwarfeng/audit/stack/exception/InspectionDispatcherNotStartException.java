package com.dwarfeng.audit.stack.exception;

/**
 * 调度器未启动异常。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionDispatcherNotStartException extends InspectionDispatcherException {

    private static final long serialVersionUID = -8609319538617167449L;

    public InspectionDispatcherNotStartException() {
    }

    public InspectionDispatcherNotStartException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "调度器未启动";
    }
}
