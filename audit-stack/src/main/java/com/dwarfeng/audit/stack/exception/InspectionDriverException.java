package com.dwarfeng.audit.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 驱动器异常。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionDriverException extends HandlerException {

    private static final long serialVersionUID = -8117470504321589790L;

    public InspectionDriverException() {
    }

    public InspectionDriverException(String message, Throwable cause) {
        super(message, cause);
    }

    public InspectionDriverException(String message) {
        super(message);
    }

    public InspectionDriverException(Throwable cause) {
        super(cause);
    }
}
