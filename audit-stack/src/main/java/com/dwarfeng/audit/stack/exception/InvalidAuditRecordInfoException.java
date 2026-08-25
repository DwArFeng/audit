package com.dwarfeng.audit.stack.exception;

import com.dwarfeng.audit.stack.bean.dto.AuditRecordInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 无效的审计记录信息异常。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class InvalidAuditRecordInfoException extends HandlerException {

    private static final long serialVersionUID = -6207710858405444019L;

    private final AuditRecordInfo auditRecordInfo;

    public InvalidAuditRecordInfoException(AuditRecordInfo auditRecordInfo) {
        this.auditRecordInfo = auditRecordInfo;
    }

    public InvalidAuditRecordInfoException(Throwable cause, AuditRecordInfo auditRecordInfo) {
        super(cause);
        this.auditRecordInfo = auditRecordInfo;
    }

    @Override
    public String getMessage() {
        return "无效的审计记录信息: " + auditRecordInfo;
    }
}
