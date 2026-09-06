package com.dwarfeng.audit.stack.exception;

import com.dwarfeng.audit.stack.bean.key.InspectorVariableKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 审计器变量不存在异常。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectorVariableNotExistsException extends HandlerException {

    private static final long serialVersionUID = 7439568552240414247L;

    private final InspectorVariableKey inspectorVariableKey;

    public InspectorVariableNotExistsException(InspectorVariableKey inspectorVariableKey) {
        this.inspectorVariableKey = inspectorVariableKey;
    }

    public InspectorVariableNotExistsException(Throwable cause, InspectorVariableKey inspectorVariableKey) {
        super(cause);
        this.inspectorVariableKey = inspectorVariableKey;
    }

    @Override
    public String getMessage() {
        return "审计器变量 " + inspectorVariableKey + " 不存在";
    }
}
