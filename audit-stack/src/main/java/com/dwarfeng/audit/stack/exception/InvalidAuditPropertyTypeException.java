package com.dwarfeng.audit.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 无效的审计属性类型异常。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class InvalidAuditPropertyTypeException extends HandlerException {

    private static final long serialVersionUID = -8038520213713592492L;

    private final String propertyId;
    private final int propertyType;

    public InvalidAuditPropertyTypeException(String propertyId, int propertyType) {
        this.propertyId = propertyId;
        this.propertyType = propertyType;
    }

    public InvalidAuditPropertyTypeException(Throwable cause, String propertyId, int propertyType) {
        super(cause);
        this.propertyId = propertyId;
        this.propertyType = propertyType;
    }

    @Override
    public String getMessage() {
        return "审计属性 " + propertyId + " 的属性类型无效: " + propertyType;
    }
}
