package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.util.Constraints;
import com.dwarfeng.audit.stack.bean.dto.InspectionTaskUpdateModalInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 自动审计任务锚点信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class WebInputInspectionTaskUpdateModalInfo implements Bean {

    private static final long serialVersionUID = 4461191163968694255L;

    public static InspectionTaskUpdateModalInfo toStackBean(WebInputInspectionTaskUpdateModalInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        }
        return new InspectionTaskUpdateModalInfo(
                WebInputLongIdKey.toStackBean(webInput.getInspectionTaskKey()),
                webInput.getAnchorMessage()
        );
    }

    @JSONField(name = "inspection_task_key", ordinal = 1)
    @NotNull
    @Valid
    private WebInputLongIdKey inspectionTaskKey;

    @JSONField(name = "anchor_message", ordinal = 2)
    @NotNull
    @Length(max = Constraints.LENGTH_MESSAGE)
    private String anchorMessage;

    public WebInputInspectionTaskUpdateModalInfo() {
    }

    public WebInputLongIdKey getInspectionTaskKey() {
        return inspectionTaskKey;
    }

    public void setInspectionTaskKey(WebInputLongIdKey inspectionTaskKey) {
        this.inspectionTaskKey = inspectionTaskKey;
    }

    public String getAnchorMessage() {
        return anchorMessage;
    }

    public void setAnchorMessage(String anchorMessage) {
        this.anchorMessage = anchorMessage;
    }

    @Override
    public String toString() {
        return "WebInputInspectionTaskUpdateModalInfo{" +
                "inspectionTaskKey=" + inspectionTaskKey +
                ", anchorMessage='" + anchorMessage + '\'' +
                '}';
    }
}
