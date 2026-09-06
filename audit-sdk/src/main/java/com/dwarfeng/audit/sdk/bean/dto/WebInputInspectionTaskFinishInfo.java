package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.dto.InspectionTaskFinishInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 自动审计任务完成信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class WebInputInspectionTaskFinishInfo implements Bean {

    private static final long serialVersionUID = 2232721176873852776L;

    public static InspectionTaskFinishInfo toStackBean(WebInputInspectionTaskFinishInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        }
        return new InspectionTaskFinishInfo(WebInputLongIdKey.toStackBean(webInput.getInspectionTaskKey()));
    }

    @JSONField(name = "inspection_task_key", ordinal = 1)
    @NotNull
    @Valid
    private WebInputLongIdKey inspectionTaskKey;

    public WebInputInspectionTaskFinishInfo() {
    }

    public WebInputLongIdKey getInspectionTaskKey() {
        return inspectionTaskKey;
    }

    public void setInspectionTaskKey(WebInputLongIdKey inspectionTaskKey) {
        this.inspectionTaskKey = inspectionTaskKey;
    }

    @Override
    public String toString() {
        return "WebInputInspectionTaskFinishInfo{" +
                "inspectionTaskKey=" + inspectionTaskKey +
                '}';
    }
}
