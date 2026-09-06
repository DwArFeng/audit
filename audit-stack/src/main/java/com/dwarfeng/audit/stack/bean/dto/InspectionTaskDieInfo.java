package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 自动审计任务死亡信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionTaskDieInfo implements Dto {

    private static final long serialVersionUID = -7035553498534390852L;

    private LongIdKey inspectionTaskKey;

    public InspectionTaskDieInfo() {
    }

    public InspectionTaskDieInfo(LongIdKey inspectionTaskKey) {
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
        return "InspectionTaskDieInfo{" +
                "inspectionTaskKey=" + inspectionTaskKey +
                '}';
    }
}
