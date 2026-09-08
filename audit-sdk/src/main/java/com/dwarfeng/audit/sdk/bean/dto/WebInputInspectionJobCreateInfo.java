package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.dto.InspectionJobCreateInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 自动审计作业创建信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class WebInputInspectionJobCreateInfo implements Bean {

    private static final long serialVersionUID = -6585845885947102303L;

    public static InspectionJobCreateInfo toStackBean(WebInputInspectionJobCreateInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        }
        return new InspectionJobCreateInfo(WebInputLongIdKey.toStackBean(webInput.getInspectionKey()));
    }

    @JSONField(name = "inspection_key", ordinal = 1)
    @NotNull
    @Valid
    private WebInputLongIdKey inspectionKey;

    public WebInputInspectionJobCreateInfo() {
    }

    public WebInputLongIdKey getInspectionKey() {
        return inspectionKey;
    }

    public void setInspectionKey(WebInputLongIdKey inspectionKey) {
        this.inspectionKey = inspectionKey;
    }

    @Override
    public String toString() {
        return "WebInputInspectionJobCreateInfo{" +
                "inspectionKey=" + inspectionKey +
                '}';
    }
}
