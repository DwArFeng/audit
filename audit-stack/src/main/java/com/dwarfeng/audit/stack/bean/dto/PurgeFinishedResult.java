package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 清除完成结果。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class PurgeFinishedResult implements Dto {

    private static final long serialVersionUID = 2026725585199429942L;

    private int taskDeletionCount;
    private boolean taskDivergent;

    public PurgeFinishedResult() {
    }

    public PurgeFinishedResult(int taskDeletionCount, boolean taskDivergent) {
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
        return "PurgeFinishedResult{" +
                "taskDeletionCount=" + taskDeletionCount +
                ", taskDivergent=" + taskDivergent +
                '}';
    }
}
