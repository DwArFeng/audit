package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 自动审计任务心跳信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionTaskBeatInfo implements Dto {

    private static final long serialVersionUID = 2507349191774053363L;

    private LongIdKey inspectionTaskKey;

    public InspectionTaskBeatInfo() {
    }

    public InspectionTaskBeatInfo(LongIdKey inspectionTaskKey) {
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
        return "InspectionTaskBeatInfo{" +
                "inspectionTaskKey=" + inspectionTaskKey +
                '}';
    }
}
