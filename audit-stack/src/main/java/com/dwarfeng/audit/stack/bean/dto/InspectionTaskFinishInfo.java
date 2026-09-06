package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 自动审计任务完成信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionTaskFinishInfo implements Dto {

    private static final long serialVersionUID = -4907377071559078637L;

    private LongIdKey inspectionTaskKey;

    public InspectionTaskFinishInfo() {
    }

    public InspectionTaskFinishInfo(LongIdKey inspectionTaskKey) {
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
        return "InspectionTaskFinishInfo{" +
                "inspectionTaskKey=" + inspectionTaskKey +
                '}';
    }
}
