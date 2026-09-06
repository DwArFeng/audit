package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.dto.InspectionTaskBeatInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 自动审计任务心跳信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class WebInputInspectionTaskBeatInfo implements Bean {

    private static final long serialVersionUID = -7789109538566044071L;

    public static InspectionTaskBeatInfo toStackBean(WebInputInspectionTaskBeatInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        }
        return new InspectionTaskBeatInfo(WebInputLongIdKey.toStackBean(webInput.getInspectionTaskKey()));
    }

    @JSONField(name = "inspection_task_key", ordinal = 1)
    @NotNull
    @Valid
    private WebInputLongIdKey inspectionTaskKey;

    public WebInputInspectionTaskBeatInfo() {
    }

    public WebInputLongIdKey getInspectionTaskKey() {
        return inspectionTaskKey;
    }

    public void setInspectionTaskKey(WebInputLongIdKey inspectionTaskKey) {
        this.inspectionTaskKey = inspectionTaskKey;
    }

    @Override
    public String toString() {
        return "WebInputInspectionTaskBeatInfo{" +
                "inspectionTaskKey=" + inspectionTaskKey +
                '}';
    }
}
