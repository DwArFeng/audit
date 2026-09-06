package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.util.Constraints;
import com.dwarfeng.audit.stack.bean.dto.InspectionAlarmCreateInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 自动审计报警创建信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class WebInputInspectionAlarmCreateInfo implements Bean {

    private static final long serialVersionUID = 1107864737320960510L;

    public static InspectionAlarmCreateInfo toStackBean(
            WebInputInspectionAlarmCreateInfo webInputInspectionAlarmCreateInfo
    ) {
        if (Objects.isNull(webInputInspectionAlarmCreateInfo)) {
            return null;
        } else {
            return new InspectionAlarmCreateInfo(
                    WebInputLongIdKey.toStackBean(webInputInspectionAlarmCreateInfo.getInspectionKey()),
                    WebInputLongIdKey.toStackBean(webInputInspectionAlarmCreateInfo.getInspectionTaskKey()),
                    WebInputLongIdKey.toStackBean(webInputInspectionAlarmCreateInfo.getInspectorInfoKey()),
                    webInputInspectionAlarmCreateInfo.getType(),
                    webInputInspectionAlarmCreateInfo.getMessage()
            );
        }
    }

    @JSONField(name = "inspectionKey")
    @Valid
    @NotNull
    private WebInputLongIdKey inspectionKey;

    @JSONField(name = "inspectionTaskKey")
    @Valid
    @NotNull
    private WebInputLongIdKey inspectionTaskKey;

    @JSONField(name = "inspectorInfoKey")
    @Valid
    @NotNull
    private WebInputLongIdKey inspectorInfoKey;

    @JSONField(name = "type")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_TYPE)
    private String type;

    @JSONField(name = "message")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_MESSAGE)
    private String message;

    public WebInputInspectionAlarmCreateInfo() {
    }

    public WebInputLongIdKey getInspectionKey() {
        return inspectionKey;
    }

    public void setInspectionKey(WebInputLongIdKey inspectionKey) {
        this.inspectionKey = inspectionKey;
    }

    public WebInputLongIdKey getInspectionTaskKey() {
        return inspectionTaskKey;
    }

    public void setInspectionTaskKey(WebInputLongIdKey inspectionTaskKey) {
        this.inspectionTaskKey = inspectionTaskKey;
    }

    public WebInputLongIdKey getInspectorInfoKey() {
        return inspectorInfoKey;
    }

    public void setInspectorInfoKey(WebInputLongIdKey inspectorInfoKey) {
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
        return "WebInputInspectionAlarmCreateInfo{" +
                "inspectionKey=" + inspectionKey +
                ", inspectionTaskKey=" + inspectionTaskKey +
                ", inspectorInfoKey=" + inspectorInfoKey +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
