package com.dwarfeng.audit.stack.exception;

/**
 * 调度器执行异常。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionDispatcherExecutionException extends InspectionDispatcherException {

    private static final long serialVersionUID = -5110416551229435474L;

    public InspectionDispatcherExecutionException() {
    }

    public InspectionDispatcherExecutionException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "调度器执行异常";
    }
}
