package com.dwarfeng.audit.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 审计器信息与自动审计归属不匹配异常。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectorInfoInspectionMismatchException extends HandlerException {

    private static final long serialVersionUID = -2689603429932677741L;

    private final LongIdKey inspectorInfoKey;
    private final LongIdKey inspectorInspectionKey;
    private final LongIdKey expectedInspectionKey;

    public InspectorInfoInspectionMismatchException(
            LongIdKey inspectorInfoKey, LongIdKey inspectorInspectionKey, LongIdKey expectedInspectionKey
    ) {
        this.inspectorInfoKey = inspectorInfoKey;
        this.inspectorInspectionKey = inspectorInspectionKey;
        this.expectedInspectionKey = expectedInspectionKey;
    }

    public InspectorInfoInspectionMismatchException(
            Throwable cause,
            LongIdKey inspectorInfoKey, LongIdKey inspectorInspectionKey, LongIdKey expectedInspectionKey
    ) {
        super(cause);
        this.inspectorInfoKey = inspectorInfoKey;
        this.inspectorInspectionKey = inspectorInspectionKey;
        this.expectedInspectionKey = expectedInspectionKey;
    }

    public LongIdKey getInspectorInfoKey() {
        return inspectorInfoKey;
    }

    public LongIdKey getInspectorInspectionKey() {
        return inspectorInspectionKey;
    }

    public LongIdKey getExpectedInspectionKey() {
        return expectedInspectionKey;
    }

    @Override
    public String getMessage() {
        return "审计器信息与自动审计归属不匹配, 审计器信息主键: " + inspectorInfoKey + ", 审计器所属自动审计: " +
                inspectorInspectionKey + ", 期望自动审计: " + expectedInspectionKey;
    }
}
