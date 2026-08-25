package com.dwarfeng.audit.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 审计记录处理器已停止异常。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class AuditRecordHandlerStoppedException extends HandlerException {

    private static final long serialVersionUID = -3462413080074377427L;

    @Override
    public String getMessage() {
        return "记录处理器尚未启动或已经停止";
    }
}
