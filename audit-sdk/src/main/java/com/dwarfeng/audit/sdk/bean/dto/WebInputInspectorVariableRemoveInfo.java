package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.util.Constraints;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableRemoveInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 审计器变量删除信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class WebInputInspectorVariableRemoveInfo implements Bean {

    private static final long serialVersionUID = 7850883022371713593L;

    public static InspectorVariableRemoveInfo toStackBean(
            WebInputInspectorVariableRemoveInfo webInputInspectorVariableRemoveInfo
    ) {
        if (Objects.isNull(webInputInspectorVariableRemoveInfo)) {
            return null;
        } else {
            return new InspectorVariableRemoveInfo(
                    WebInputLongIdKey.toStackBean(webInputInspectorVariableRemoveInfo.getInspectorInfoKey()),
                    webInputInspectorVariableRemoveInfo.getInspectorVariableId()
            );
        }
    }

    @JSONField(name = "inspectorInfoKey")
    @Valid
    @NotNull
    private WebInputLongIdKey inspectorInfoKey;

    @JSONField(name = "inspectorVariableId")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_STRING_ID)
    private String inspectorVariableId;

    public WebInputInspectorVariableRemoveInfo() {
    }

    public WebInputLongIdKey getInspectorInfoKey() {
        return inspectorInfoKey;
    }

    public void setInspectorInfoKey(WebInputLongIdKey inspectorInfoKey) {
        this.inspectorInfoKey = inspectorInfoKey;
    }

    public String getInspectorVariableId() {
        return inspectorVariableId;
    }

    public void setInspectorVariableId(String inspectorVariableId) {
        this.inspectorVariableId = inspectorVariableId;
    }

    @Override
    public String toString() {
        return "WebInputInspectorVariableRemoveInfo{" +
                "inspectorInfoKey=" + inspectorInfoKey +
                ", inspectorVariableId='" + inspectorVariableId + '\'' +
                '}';
    }
}
