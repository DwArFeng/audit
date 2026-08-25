package com.dwarfeng.audit.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 审计类别已禁用异常。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class AuditCategoryDisabledException extends HandlerException {

    private static final long serialVersionUID = 5069927100156425501L;

    private final StringIdKey categoryKey;

    public AuditCategoryDisabledException(StringIdKey categoryKey) {
        this.categoryKey = categoryKey;
    }

    public AuditCategoryDisabledException(Throwable cause, StringIdKey categoryKey) {
        super(cause);
        this.categoryKey = categoryKey;
    }

    @Override
    public String getMessage() {
        return "审计类别 " + categoryKey + " 已禁用";
    }
}
