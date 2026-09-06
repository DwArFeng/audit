package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.dto.InspectionTaskCreateInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 自动审计任务创建信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class WebInputInspectionTaskCreateInfo implements Bean {

    private static final long serialVersionUID = -2047140224893721695L;

    public static InspectionTaskCreateInfo toStackBean(WebInputInspectionTaskCreateInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        }
        return new InspectionTaskCreateInfo(WebInputLongIdKey.toStackBean(webInput.getInspectionKey()));
    }

    @JSONField(name = "inspection_key", ordinal = 1)
    @NotNull
    @Valid
    private WebInputLongIdKey inspectionKey;

    public WebInputInspectionTaskCreateInfo() {
    }

    public WebInputLongIdKey getInspectionKey() {
        return inspectionKey;
    }

    public void setInspectionKey(WebInputLongIdKey inspectionKey) {
        this.inspectionKey = inspectionKey;
    }

    @Override
    public String toString() {
        return "WebInputInspectionTaskCreateInfo{" +
                "inspectionKey=" + inspectionKey +
                '}';
    }
}
