package com.dwarfeng.audit.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.entity.InspectionTaskEvent;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 自动审计任务事件。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class JSFixedFastJsonInspectionTaskEvent implements Bean {

    private static final long serialVersionUID = -1988960813770782983L;

    public static JSFixedFastJsonInspectionTaskEvent of(InspectionTaskEvent inspectionTaskEvent) {
        if (Objects.isNull(inspectionTaskEvent)) {
            return null;
        } else {
            return new JSFixedFastJsonInspectionTaskEvent(
                    JSFixedFastJsonLongIdKey.of(inspectionTaskEvent.getKey()),
                    JSFixedFastJsonLongIdKey.of(inspectionTaskEvent.getInspectionTaskKey()),
                    inspectionTaskEvent.getHappenedDate(),
                    inspectionTaskEvent.getMessage()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "inspection_task_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey inspectionTaskKey;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    @JSONField(name = "message", ordinal = 4)
    private String message;

    public JSFixedFastJsonInspectionTaskEvent() {
    }

    public JSFixedFastJsonInspectionTaskEvent(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey inspectionTaskKey, Date happenedDate, String message
    ) {
        this.key = key;
        this.inspectionTaskKey = inspectionTaskKey;
        this.happenedDate = happenedDate;
        this.message = message;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getInspectionTaskKey() {
        return inspectionTaskKey;
    }

    public void setInspectionTaskKey(JSFixedFastJsonLongIdKey inspectionTaskKey) {
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
        return "JSFixedFastJsonInspectionTaskEvent{" +
                "key=" + key +
                ", inspectionTaskKey=" + inspectionTaskKey +
                ", happenedDate=" + happenedDate +
                ", message='" + message + '\'' +
                '}';
    }
}
