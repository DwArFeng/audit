package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 自动审计报警创建信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionAlarmCreateInfo implements Dto {

    private static final long serialVersionUID = -1031558428883890938L;

    private LongIdKey inspectionKey;
    private LongIdKey inspectionTaskKey;
    private LongIdKey inspectorInfoKey;
    private String type;
    private String message;

    public InspectionAlarmCreateInfo() {
    }

    public InspectionAlarmCreateInfo(
            LongIdKey inspectionKey, LongIdKey inspectionTaskKey, LongIdKey inspectorInfoKey, String type,
            String message
    ) {
        this.inspectionKey = inspectionKey;
        this.inspectionTaskKey = inspectionTaskKey;
        this.inspectorInfoKey = inspectorInfoKey;
        this.type = type;
        this.message = message;
    }

    public LongIdKey getInspectionKey() {
        return inspectionKey;
    }

    public void setInspectionKey(LongIdKey inspectionKey) {
        this.inspectionKey = inspectionKey;
    }

    public LongIdKey getInspectionTaskKey() {
        return inspectionTaskKey;
    }

    public void setInspectionTaskKey(LongIdKey inspectionTaskKey) {
        this.inspectionTaskKey = inspectionTaskKey;
    }

    public LongIdKey getInspectorInfoKey() {
        return inspectorInfoKey;
    }

    public void setInspectorInfoKey(LongIdKey inspectorInfoKey) {
        this.inspectorInfoKey = inspectorInfoKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "InspectionAlarmCreateInfo{" +
                "inspectionKey=" + inspectionKey +
                ", inspectionTaskKey=" + inspectionTaskKey +
                ", inspectorInfoKey=" + inspectorInfoKey +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
