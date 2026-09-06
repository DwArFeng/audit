package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 自动审计任务失败信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionTaskFailInfo implements Dto {

    private static final long serialVersionUID = 3837708227786992690L;

    private LongIdKey inspectionTaskKey;

    public InspectionTaskFailInfo() {
    }

    public InspectionTaskFailInfo(LongIdKey inspectionTaskKey) {
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
        return "InspectionTaskFailInfo{" +
                "inspectionTaskKey=" + inspectionTaskKey +
                '}';
    }
}
