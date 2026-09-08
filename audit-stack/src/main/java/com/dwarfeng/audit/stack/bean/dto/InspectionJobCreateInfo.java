package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 自动审计作业创建信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionJobCreateInfo implements Dto {

    private static final long serialVersionUID = 2835772072691625323L;

    private LongIdKey inspectionKey;

    public InspectionJobCreateInfo() {
    }

    public InspectionJobCreateInfo(LongIdKey inspectionKey) {
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
        return "InspectionJobCreateInfo{" +
                "inspectionKey=" + inspectionKey +
                '}';
    }
}
