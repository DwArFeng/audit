package com.dwarfeng.audit.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 审计属性值类型不匹配异常。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class AuditPropertyValueTypeMismatchException extends HandlerException {

    private static final long serialVersionUID = 4171625219592075340L;

    private final String propertyId;
    private final Class<?> expectedType;
    private final Class<?> actualType;

    public AuditPropertyValueTypeMismatchException(String propertyId, Class<?> expectedType, Class<?> actualType) {
        this.propertyId = propertyId;
        this.expectedType = expectedType;
        this.actualType = actualType;
    }

    public AuditPropertyValueTypeMismatchException(
            Throwable cause, String propertyId, Class<?> expectedType, Class<?> actualType
    ) {
        super(cause);
        this.propertyId = propertyId;
        this.expectedType = expectedType;
        this.actualType = actualType;
    }

    @Override
    public String getMessage() {
        return "审计属性 " + propertyId + " 的值类型不匹配, 期望类型: " + expectedType.getName()
                + ", 实际类型: " + actualType.getName();
    }
}
