package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 自动审计作业执行信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionJobExecuteInfo implements Dto {

    private static final long serialVersionUID = 1454195152000925435L;

    private LongIdKey inspectionTaskKey;

    public InspectionJobExecuteInfo() {
    }

    public InspectionJobExecuteInfo(LongIdKey inspectionTaskKey) {
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
        return "InspectionJobExecuteInfo{" +
                "inspectionTaskKey=" + inspectionTaskKey +
                '}';
    }
}
