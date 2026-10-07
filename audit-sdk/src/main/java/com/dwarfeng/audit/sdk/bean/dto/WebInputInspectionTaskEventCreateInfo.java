package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.dto.InspectionTaskEventCreateInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

/**
 * WebInput 自动审计任务事件创建信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class WebInputInspectionTaskEventCreateInfo implements Bean {

    private static final long serialVersionUID = 2267452033148449523L;

    public static InspectionTaskEventCreateInfo toStackBean(WebInputInspectionTaskEventCreateInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        }
        return new InspectionTaskEventCreateInfo(
                WebInputLongIdKey.toStackBean(webInput.getInspectionTaskKey()),
                webInput.getHappenedDate(),
                webInput.getMessage()
        );
    }

    @JSONField(name = "inspection_task_key", ordinal = 1)
    @NotNull
    @Valid
    private WebInputLongIdKey inspectionTaskKey;

    @JSONField(name = "happened_date", ordinal = 2)
    @NotNull
    private Date happenedDate;

    @JSONField(name = "message", ordinal = 3)
    private String message;

    public WebInputInspectionTaskEventCreateInfo() {
    }

    public WebInputLongIdKey getInspectionTaskKey() {
        return inspectionTaskKey;
    }

    public void setInspectionTaskKey(WebInputLongIdKey inspectionTaskKey) {
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
        return "WebInputInspectionTaskEventCreateInfo{" +
                "inspectionTaskKey=" + inspectionTaskKey +
                ", happenedDate=" + happenedDate +
                ", message='" + message + '\'' +
                '}';
    }
}
