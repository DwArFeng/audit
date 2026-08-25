package com.dwarfeng.audit.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 审计类别不存在异常。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class AuditCategoryNotExistsException extends HandlerException {

    private static final long serialVersionUID = -8972067680447237128L;

    private final StringIdKey categoryKey;

    public AuditCategoryNotExistsException(StringIdKey categoryKey) {
        this.categoryKey = categoryKey;
    }

    public AuditCategoryNotExistsException(Throwable cause, StringIdKey categoryKey) {
        super(cause);
        this.categoryKey = categoryKey;
    }

    @Override
    public String getMessage() {
        return "审计类别 " + categoryKey + " 不存在";
    }
}
