package com.dwarfeng.audit.stack.exception;

import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 审计属性指示器不存在异常。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class AuditPropertyIndicatorNotExistsException extends HandlerException {

    private static final long serialVersionUID = 5998675036901153037L;

    private final AuditPropertyIndicatorKey indicatorKey;

    public AuditPropertyIndicatorNotExistsException(AuditPropertyIndicatorKey indicatorKey) {
        this.indicatorKey = indicatorKey;
    }

    public AuditPropertyIndicatorNotExistsException(Throwable cause, AuditPropertyIndicatorKey indicatorKey) {
        super(cause);
        this.indicatorKey = indicatorKey;
    }

    @Override
    public String getMessage() {
        return "审计属性指示器 " + indicatorKey + " 不存在";
    }
}
