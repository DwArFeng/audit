package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.dto.InspectionTaskExpireInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 自动审计任务过期信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class WebInputInspectionTaskExpireInfo implements Bean {

    private static final long serialVersionUID = -4350724241043753063L;

    public static InspectionTaskExpireInfo toStackBean(WebInputInspectionTaskExpireInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        }
        return new InspectionTaskExpireInfo(WebInputLongIdKey.toStackBean(webInput.getInspectionTaskKey()));
    }

    @JSONField(name = "inspection_task_key", ordinal = 1)
    @NotNull
    @Valid
    private WebInputLongIdKey inspectionTaskKey;

    public WebInputInspectionTaskExpireInfo() {
    }

    public WebInputLongIdKey getInspectionTaskKey() {
        return inspectionTaskKey;
    }

    public void setInspectionTaskKey(WebInputLongIdKey inspectionTaskKey) {
        this.inspectionTaskKey = inspectionTaskKey;
    }

    @Override
    public String toString() {
        return "WebInputInspectionTaskExpireInfo{" +
                "inspectionTaskKey=" + inspectionTaskKey +
                '}';
    }
}
