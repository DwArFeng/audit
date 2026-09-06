package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * 自动审计任务事件创建信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionTaskEventCreateInfo implements Dto {

    private static final long serialVersionUID = -663000664619684886L;

    private LongIdKey inspectionTaskKey;
    private Date happenedDate;
    private String message;

    public InspectionTaskEventCreateInfo() {
    }

    public InspectionTaskEventCreateInfo(LongIdKey inspectionTaskKey, Date happenedDate, String message) {
        this.inspectionTaskKey = inspectionTaskKey;
        this.happenedDate = happenedDate;
        this.message = message;
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
        return "InspectionTaskEventCreateInfo{" +
                "inspectionTaskKey=" + inspectionTaskKey +
                ", happenedDate=" + happenedDate +
                ", message='" + message + '\'' +
                '}';
    }
}
