package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.dto.PurgeFinishedResult;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 清除完成结果。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class JSFixedFastJsonPurgeFinishedResult implements Dto {

    private static final long serialVersionUID = 2181471969525465836L;

    public static JSFixedFastJsonPurgeFinishedResult of(PurgeFinishedResult result) {
        if (Objects.isNull(result)) {
            return null;
        }
        return new JSFixedFastJsonPurgeFinishedResult(result.getTaskDeletionCount(), result.isTaskDivergent());
    }

    @JSONField(name = "task_deletion_count", ordinal = 1)
    private int taskDeletionCount;

    @JSONField(name = "task_divergent", ordinal = 2)
    private boolean taskDivergent;

    public JSFixedFastJsonPurgeFinishedResult() {
    }

    public JSFixedFastJsonPurgeFinishedResult(int taskDeletionCount, boolean taskDivergent) {
        this.taskDeletionCount = taskDeletionCount;
        this.taskDivergent = taskDivergent;
    }

    public int getTaskDeletionCount() {
        return taskDeletionCount;
    }

    public void setTaskDeletionCount(int taskDeletionCount) {
        this.taskDeletionCount = taskDeletionCount;
    }

    public boolean isTaskDivergent() {
        return taskDivergent;
    }

    public void setTaskDivergent(boolean taskDivergent) {
        this.taskDivergent = taskDivergent;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonPurgeFinishedResult{" +
                "taskDeletionCount=" + taskDeletionCount +
                ", taskDivergent=" + taskDivergent +
                '}';
    }
}
