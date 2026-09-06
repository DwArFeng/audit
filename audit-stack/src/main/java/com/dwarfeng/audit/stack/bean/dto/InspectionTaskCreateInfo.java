package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 自动审计任务创建信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionTaskCreateInfo implements Dto {

    private static final long serialVersionUID = 3216150458675744912L;

    private LongIdKey inspectionKey;

    public InspectionTaskCreateInfo() {
    }

    public InspectionTaskCreateInfo(LongIdKey inspectionKey) {
        this.inspectionKey = inspectionKey;
    }

    public LongIdKey getInspectionKey() {
        return inspectionKey;
    }

    public void setInspectionKey(LongIdKey inspectionKey) {
        this.inspectionKey = inspectionKey;
    }

    @Override
    public String toString() {
        return "InspectionTaskCreateInfo{" +
                "inspectionKey=" + inspectionKey +
                '}';
    }
}
