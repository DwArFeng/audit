package com.dwarfeng.audit.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 审计器信息不存在异常。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectorInfoNotExistsException extends HandlerException {

    private static final long serialVersionUID = -6772276706625888236L;

    private final LongIdKey inspectorInfoKey;

    public InspectorInfoNotExistsException(LongIdKey inspectorInfoKey) {
        this.inspectorInfoKey = inspectorInfoKey;
    }

    public InspectorInfoNotExistsException(Throwable cause, LongIdKey inspectorInfoKey) {
        super(cause);
        this.inspectorInfoKey = inspectorInfoKey;
    }

    @Override
    public String getMessage() {
        return "审计器信息 " + inspectorInfoKey + " 不存在";
    }
}
