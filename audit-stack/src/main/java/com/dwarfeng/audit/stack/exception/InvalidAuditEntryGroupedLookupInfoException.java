package com.dwarfeng.audit.stack.exception;

import com.dwarfeng.audit.stack.bean.dto.AuditEntryGroupedLookupInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 无效的审计条目分组查询信息异常。
 *
 * <p>
 * 异常消息不展开查询树，避免循环引用的非法查询信息在生成异常消息时再次引发递归错误。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class InvalidAuditEntryGroupedLookupInfoException extends HandlerException {

    private static final long serialVersionUID = 7394297576175061878L;

    private final AuditEntryGroupedLookupInfo info;
    private final String detail;

    public InvalidAuditEntryGroupedLookupInfoException(AuditEntryGroupedLookupInfo info, String detail) {
        this.info = info;
        this.detail = detail;
    }

    public InvalidAuditEntryGroupedLookupInfoException(
            Throwable cause, AuditEntryGroupedLookupInfo info, String detail
    ) {
        super(cause);
        this.info = info;
        this.detail = detail;
    }

    @Override
    public String getMessage() {
        return "无效的审计条目分组查询信息: " + info + ", 详细信息: " + detail;
    }
}
