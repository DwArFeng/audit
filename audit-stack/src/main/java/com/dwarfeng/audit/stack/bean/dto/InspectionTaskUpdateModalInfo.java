package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 自动审计任务模态更新信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionTaskUpdateModalInfo implements Dto {

    private static final long serialVersionUID = -4364933368805937719L;

    private LongIdKey inspectionTaskKey;
    private String anchorMessage;

    public InspectionTaskUpdateModalInfo() {
    }

    public InspectionTaskUpdateModalInfo(LongIdKey inspectionTaskKey, String anchorMessage) {
        this.inspectionTaskKey = inspectionTaskKey;
        this.anchorMessage = anchorMessage;
    }

    public LongIdKey getInspectionTaskKey() {
        return inspectionTaskKey;
    }

    public void setInspectionTaskKey(LongIdKey inspectionTaskKey) {
        this.inspectionTaskKey = inspectionTaskKey;
    }

    public String getAnchorMessage() {
        return anchorMessage;
    }

    public void setAnchorMessage(String anchorMessage) {
        this.anchorMessage = anchorMessage;
    }

    @Override
    public String toString() {
        return "InspectionTaskUpdateModalInfo{" +
                "inspectionTaskKey=" + inspectionTaskKey +
                ", anchorMessage='" + anchorMessage + '\'' +
                '}';
    }
}
