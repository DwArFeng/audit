package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 自动审计作业创建结果。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionJobCreateResult implements Dto {

    private static final long serialVersionUID = 7503629050403448127L;

    private LongIdKey inspectionTaskKey;

    public InspectionJobCreateResult() {
    }

    public InspectionJobCreateResult(LongIdKey inspectionTaskKey) {
        this.inspectionTaskKey = inspectionTaskKey;
    }

    public LongIdKey getInspectionTaskKey() {
        return inspectionTaskKey;
    }

    public void setInspectionTaskKey(LongIdKey inspectionTaskKey) {
        this.inspectionTaskKey = inspectionTaskKey;
    }

    @Override
    public String toString() {
        return "InspectionJobCreateResult{" +
                "inspectionTaskKey=" + inspectionTaskKey +
                '}';
    }
}
