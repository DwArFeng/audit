package com.dwarfeng.audit.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.entity.InspectionTaskEvent;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 自动审计任务事件。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class FastJsonInspectionTaskEvent implements Bean {

    private static final long serialVersionUID = -9211880768879947563L;

    public static FastJsonInspectionTaskEvent of(InspectionTaskEvent inspectionTaskEvent) {
        if (Objects.isNull(inspectionTaskEvent)) {
            return null;
        } else {
            return new FastJsonInspectionTaskEvent(
                    FastJsonLongIdKey.of(inspectionTaskEvent.getKey()),
                    FastJsonLongIdKey.of(inspectionTaskEvent.getInspectionTaskKey()),
                    inspectionTaskEvent.getHappenedDate(),
                    inspectionTaskEvent.getMessage()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "inspection_task_key", ordinal = 2)
    private FastJsonLongIdKey inspectionTaskKey;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    @JSONField(name = "message", ordinal = 4)
    private String message;

    public FastJsonInspectionTaskEvent() {
    }

    public FastJsonInspectionTaskEvent(
            FastJsonLongIdKey key, FastJsonLongIdKey inspectionTaskKey, Date happenedDate, String message
    ) {
        this.key = key;
        this.inspectionTaskKey = inspectionTaskKey;
        this.happenedDate = happenedDate;
        this.message = message;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
        this.key = key;
    }

    public FastJsonLongIdKey getInspectionTaskKey() {
        return inspectionTaskKey;
    }

    public void setInspectionTaskKey(FastJsonLongIdKey inspectionTaskKey) {
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
        return "FastJsonInspectionTaskEvent{" +
                "key=" + key +
                ", inspectionTaskKey=" + inspectionTaskKey +
                ", happenedDate=" + happenedDate +
                ", message='" + message + '\'' +
                '}';
    }
}
