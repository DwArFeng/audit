package com.dwarfeng.audit.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.entity.InspectionAlarm;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 自动审计报警。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class FastJsonInspectionAlarm implements Bean {

    private static final long serialVersionUID = -3366381180709800525L;

    public static FastJsonInspectionAlarm of(InspectionAlarm inspectionAlarm) {
        if (Objects.isNull(inspectionAlarm)) {
            return null;
        } else {
            return new FastJsonInspectionAlarm(
                    FastJsonLongIdKey.of(inspectionAlarm.getKey()),
                    FastJsonLongIdKey.of(inspectionAlarm.getInspectionKey()),
                    FastJsonLongIdKey.of(inspectionAlarm.getInspectionTaskKey()),
                    FastJsonLongIdKey.of(inspectionAlarm.getInspectorInfoKey()),
                    inspectionAlarm.getHappenedDate(),
                    inspectionAlarm.getType(),
                    inspectionAlarm.getMessage()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "inspection_key", ordinal = 2)
    private FastJsonLongIdKey inspectionKey;

    @JSONField(name = "inspection_task_key", ordinal = 3)
    private FastJsonLongIdKey inspectionTaskKey;

    @JSONField(name = "inspector_info_key", ordinal = 4)
    private FastJsonLongIdKey inspectorInfoKey;

    @JSONField(name = "happened_date", ordinal = 5)
    private Date happenedDate;

    @JSONField(name = "type", ordinal = 6)
    private String type;

    @JSONField(name = "message", ordinal = 7)
    private String message;

    public FastJsonInspectionAlarm() {
    }

    public FastJsonInspectionAlarm(
            FastJsonLongIdKey key, FastJsonLongIdKey inspectionKey, FastJsonLongIdKey inspectionTaskKey,
            FastJsonLongIdKey inspectorInfoKey, Date happenedDate, String type, String message
    ) {
        this.key = key;
        this.inspectionKey = inspectionKey;
        this.inspectionTaskKey = inspectionTaskKey;
        this.inspectorInfoKey = inspectorInfoKey;
        this.happenedDate = happenedDate;
        this.type = type;
        this.message = message;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
        this.key = key;
    }

    public FastJsonLongIdKey getInspectionKey() {
        return inspectionKey;
    }

    public void setInspectionKey(FastJsonLongIdKey inspectionKey) {
        this.inspectionKey = inspectionKey;
    }

    public FastJsonLongIdKey getInspectionTaskKey() {
        return inspectionTaskKey;
    }

    public void setInspectionTaskKey(FastJsonLongIdKey inspectionTaskKey) {
        this.inspectionTaskKey = inspectionTaskKey;
    }

    public FastJsonLongIdKey getInspectorInfoKey() {
        return inspectorInfoKey;
    }

    public void setInspectorInfoKey(FastJsonLongIdKey inspectorInfoKey) {
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
        return "FastJsonInspectionAlarm{" +
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
