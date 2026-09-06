package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.util.Constraints;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableInspectInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 审计器变量查看信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class WebInputInspectorVariableInspectInfo implements Bean {

    private static final long serialVersionUID = 2562629374333287761L;

    public static InspectorVariableInspectInfo toStackBean(
            WebInputInspectorVariableInspectInfo webInputInspectorVariableInspectInfo
    ) {
        if (Objects.isNull(webInputInspectorVariableInspectInfo)) {
            return null;
        } else {
            return new InspectorVariableInspectInfo(
                    WebInputLongIdKey.toStackBean(webInputInspectorVariableInspectInfo.getInspectorInfoKey()),
                    webInputInspectorVariableInspectInfo.getInspectorVariableId()
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

    public WebInputInspectorVariableInspectInfo() {
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
        return "WebInputInspectorVariableInspectInfo{" +
                "inspectorInfoKey=" + inspectorInfoKey +
                ", inspectorVariableId='" + inspectorVariableId + '\'' +
                '}';
    }
}
