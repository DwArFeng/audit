package com.dwarfeng.audit.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.entity.InspectionAlarm;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 自动审计报警。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class JSFixedFastJsonInspectionAlarm implements Bean {

    private static final long serialVersionUID = -977039038932510346L;

    public static JSFixedFastJsonInspectionAlarm of(InspectionAlarm inspectionAlarm) {
        if (Objects.isNull(inspectionAlarm)) {
            return null;
        } else {
            return new JSFixedFastJsonInspectionAlarm(
                    JSFixedFastJsonLongIdKey.of(inspectionAlarm.getKey()),
                    JSFixedFastJsonLongIdKey.of(inspectionAlarm.getInspectionKey()),
                    JSFixedFastJsonLongIdKey.of(inspectionAlarm.getInspectionTaskKey()),
                    JSFixedFastJsonLongIdKey.of(inspectionAlarm.getInspectorInfoKey()),
                    inspectionAlarm.getHappenedDate(),
                    inspectionAlarm.getType(),
                    inspectionAlarm.getMessage()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "inspection_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey inspectionKey;

    @JSONField(name = "inspection_task_key", ordinal = 3)
    private JSFixedFastJsonLongIdKey inspectionTaskKey;

    @JSONField(name = "inspector_info_key", ordinal = 4)
    private JSFixedFastJsonLongIdKey inspectorInfoKey;

    @JSONField(name = "happened_date", ordinal = 5)
    private Date happenedDate;

    @JSONField(name = "type", ordinal = 6)
    private String type;

    @JSONField(name = "message", ordinal = 7)
    private String message;

    public JSFixedFastJsonInspectionAlarm() {
    }

    public JSFixedFastJsonInspectionAlarm(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey inspectionKey,
            JSFixedFastJsonLongIdKey inspectionTaskKey, JSFixedFastJsonLongIdKey inspectorInfoKey, Date happenedDate,
            String type, String message
    ) {
        this.key = key;
        this.inspectionKey = inspectionKey;
        this.inspectionTaskKey = inspectionTaskKey;
        this.inspectorInfoKey = inspectorInfoKey;
        this.happenedDate = happenedDate;
        this.type = type;
        this.message = message;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getInspectionKey() {
        return inspectionKey;
    }

    public void setInspectionKey(JSFixedFastJsonLongIdKey inspectionKey) {
        this.inspectionKey = inspectionKey;
    }

    public JSFixedFastJsonLongIdKey getInspectionTaskKey() {
        return inspectionTaskKey;
    }

    public void setInspectionTaskKey(JSFixedFastJsonLongIdKey inspectionTaskKey) {
        this.inspectionTaskKey = inspectionTaskKey;
    }

    public JSFixedFastJsonLongIdKey getInspectorInfoKey() {
        return inspectorInfoKey;
    }

    public void setInspectorInfoKey(JSFixedFastJsonLongIdKey inspectorInfoKey) {
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
        return "JSFixedFastJsonInspectionAlarm{" +
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
