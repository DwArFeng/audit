package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.dto.InspectionTaskFailInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 自动审计任务失败信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class WebInputInspectionTaskFailInfo implements Bean {

    private static final long serialVersionUID = -5331495841886418934L;

    public static InspectionTaskFailInfo toStackBean(WebInputInspectionTaskFailInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        }
        return new InspectionTaskFailInfo(WebInputLongIdKey.toStackBean(webInput.getInspectionTaskKey()));
    }

    @JSONField(name = "inspection_task_key", ordinal = 1)
    @NotNull
    @Valid
    private WebInputLongIdKey inspectionTaskKey;

    public WebInputInspectionTaskFailInfo() {
    }

    public WebInputLongIdKey getInspectionTaskKey() {
        return inspectionTaskKey;
    }

    public void setInspectionTaskKey(WebInputLongIdKey inspectionTaskKey) {
        this.inspectionTaskKey = inspectionTaskKey;
    }

    @Override
    public String toString() {
        return "WebInputInspectionTaskFailInfo{" +
                "inspectionTaskKey=" + inspectionTaskKey +
                '}';
    }
}
