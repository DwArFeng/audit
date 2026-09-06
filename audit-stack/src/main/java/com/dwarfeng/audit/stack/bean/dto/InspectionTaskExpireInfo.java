package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 自动审计任务过期信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionTaskExpireInfo implements Dto {

    private static final long serialVersionUID = 5857307010379714049L;

    private LongIdKey inspectionTaskKey;

    public InspectionTaskExpireInfo() {
    }

    public InspectionTaskExpireInfo(LongIdKey inspectionTaskKey) {
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
        return "InspectionTaskExpireInfo{" +
                "inspectionTaskKey=" + inspectionTaskKey +
                '}';
    }
}
