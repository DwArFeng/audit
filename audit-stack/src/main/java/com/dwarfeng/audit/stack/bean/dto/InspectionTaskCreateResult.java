package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 自动审计任务创建结果。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionTaskCreateResult implements Dto {

    private static final long serialVersionUID = 2188145364094986789L;

    private LongIdKey inspectionTaskKey;

    public InspectionTaskCreateResult() {
    }

    public InspectionTaskCreateResult(LongIdKey inspectionTaskKey) {
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
        return "InspectionTaskCreateResult{" +
                "inspectionTaskKey=" + inspectionTaskKey +
                '}';
    }
}
