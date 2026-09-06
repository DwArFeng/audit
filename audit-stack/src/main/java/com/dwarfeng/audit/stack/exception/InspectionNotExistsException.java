package com.dwarfeng.audit.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 自动审计不存在异常。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionNotExistsException extends HandlerException {

    private static final long serialVersionUID = -9056346689381017663L;

    private final LongIdKey inspectionKey;

    public InspectionNotExistsException(LongIdKey inspectionKey) {
        this.inspectionKey = inspectionKey;
    }

    public InspectionNotExistsException(Throwable cause, LongIdKey inspectionKey) {
        super(cause);
        this.inspectionKey = inspectionKey;
    }

    @Override
    public String getMessage() {
        return "自动审计 " + inspectionKey + " 不存在";
    }
}
