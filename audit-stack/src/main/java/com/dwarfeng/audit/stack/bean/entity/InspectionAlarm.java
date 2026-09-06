package com.dwarfeng.audit.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * 自动审计报警。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionAlarm implements Entity<LongIdKey> {

    private static final long serialVersionUID = 9058414937958115918L;

    private LongIdKey key;
    private LongIdKey inspectionKey;
    private LongIdKey inspectionTaskKey;
    private LongIdKey inspectorInfoKey;
    private Date happenedDate;
    private String type;
    private String message;

    public InspectionAlarm() {
    }

    public InspectionAlarm(
            LongIdKey key, LongIdKey inspectionKey, LongIdKey inspectionTaskKey, LongIdKey inspectorInfoKey,
            Date happenedDate, String type, String message
    ) {
        this.key = key;
        this.inspectionKey = inspectionKey;
        this.inspectionTaskKey = inspectionTaskKey;
        this.inspectorInfoKey = inspectorInfoKey;
        this.happenedDate = happenedDate;
        this.type = type;
        this.message = message;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
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

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
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
        return "InspectionAlarm{" +
                "key=" + key +
                ", inspectionKey=" + inspectionKey +
                ", inspectionTaskKey=" + inspectionTaskKey +
                ", inspectorInfoKey=" + inspectorInfoKey +
                ", happenedDate=" + happenedDate +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
