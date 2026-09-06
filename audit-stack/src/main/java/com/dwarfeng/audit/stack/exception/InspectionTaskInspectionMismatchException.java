package com.dwarfeng.audit.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 自动审计任务与自动审计归属不匹配异常。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionTaskInspectionMismatchException extends HandlerException {

    private static final long serialVersionUID = 1199816080886300352L;

    private final LongIdKey inspectionTaskKey;
    private final LongIdKey taskInspectionKey;
    private final LongIdKey expectedInspectionKey;

    public InspectionTaskInspectionMismatchException(
            LongIdKey inspectionTaskKey, LongIdKey taskInspectionKey, LongIdKey expectedInspectionKey
    ) {
        this.inspectionTaskKey = inspectionTaskKey;
        this.taskInspectionKey = taskInspectionKey;
        this.expectedInspectionKey = expectedInspectionKey;
    }

    public InspectionTaskInspectionMismatchException(
            Throwable cause, LongIdKey inspectionTaskKey, LongIdKey taskInspectionKey, LongIdKey expectedInspectionKey
    ) {
        super(cause);
        this.inspectionTaskKey = inspectionTaskKey;
        this.taskInspectionKey = taskInspectionKey;
        this.expectedInspectionKey = expectedInspectionKey;
    }

    public LongIdKey getInspectionTaskKey() {
        return inspectionTaskKey;
    }

    public LongIdKey getTaskInspectionKey() {
        return taskInspectionKey;
    }

    public LongIdKey getExpectedInspectionKey() {
        return expectedInspectionKey;
    }

    @Override
    public String getMessage() {
        return "自动审计任务与自动审计归属不匹配, 任务主键: " + inspectionTaskKey + ", 任务所属自动审计: " +
                taskInspectionKey + ", 期望自动审计: " + expectedInspectionKey;
    }
}
