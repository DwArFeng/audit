package com.dwarfeng.audit.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * 自动审计任务事件。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionTaskEvent implements Entity<LongIdKey> {

    private static final long serialVersionUID = -6824753054811668631L;

    private LongIdKey key;
    private LongIdKey inspectionTaskKey;
    private Date happenedDate;
    private String message;

    public InspectionTaskEvent() {
    }

    public InspectionTaskEvent(LongIdKey key, LongIdKey inspectionTaskKey, Date happenedDate, String message) {
        this.key = key;
        this.inspectionTaskKey = inspectionTaskKey;
        this.happenedDate = happenedDate;
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

    public LongIdKey getInspectionTaskKey() {
        return inspectionTaskKey;
    }

    public void setInspectionTaskKey(LongIdKey inspectionTaskKey) {
        this.inspectionTaskKey = inspectionTaskKey;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "InspectionTaskEvent{" +
                "key=" + key +
                ", inspectionTaskKey=" + inspectionTaskKey +
                ", happenedDate=" + happenedDate +
                ", message='" + message + '\'' +
                '}';
    }
}
