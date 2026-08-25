package com.dwarfeng.audit.stack.exception;

import com.dwarfeng.audit.stack.bean.dto.AuditEntryCompositeLookupInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 无效的审计条目组合查询信息异常。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class InvalidAuditEntryCompositeLookupInfoException extends HandlerException {

    private static final long serialVersionUID = 5593412227288380552L;

    private final AuditEntryCompositeLookupInfo info;
    private final String detail;

    public InvalidAuditEntryCompositeLookupInfoException(AuditEntryCompositeLookupInfo info, String detail) {
        this.info = info;
        this.detail = detail;
    }

    public InvalidAuditEntryCompositeLookupInfoException(
            Throwable cause, AuditEntryCompositeLookupInfo info, String detail
    ) {
        super(cause);
        this.info = info;
        this.detail = detail;
    }

    @Override
    public String getMessage() {
        return "无效的审计条目组合查询信息: " + info + ", 详细信息: " + detail;
    }
}
