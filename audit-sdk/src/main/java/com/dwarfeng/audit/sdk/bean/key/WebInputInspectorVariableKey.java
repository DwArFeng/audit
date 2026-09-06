package com.dwarfeng.audit.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.util.Constraints;
import com.dwarfeng.audit.stack.bean.key.InspectorVariableKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 审计器变量键。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class WebInputInspectorVariableKey implements Key {

    private static final long serialVersionUID = 2040193892311977439L;

    public static InspectorVariableKey toStackBean(WebInputInspectorVariableKey webInputInspectorVariableKey) {
        if (Objects.isNull(webInputInspectorVariableKey)) {
            return null;
        } else {
            return new InspectorVariableKey(
                    webInputInspectorVariableKey.getInspectorInfoLongId(),
                    webInputInspectorVariableKey.getVariableStringId()
            );
        }
    }

    @JSONField(name = "inspector_info_long_id")
    @NotNull
    private Long inspectorInfoLongId;

    @JSONField(name = "variable_string_id")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_STRING_ID)
    private String variableStringId;

    public WebInputInspectorVariableKey() {
    }

    public Long getInspectorInfoLongId() {
        return inspectorInfoLongId;
    }

    public void setInspectorInfoLongId(Long inspectorInfoLongId) {
        this.inspectorInfoLongId = inspectorInfoLongId;
    }

    public String getVariableStringId() {
        return variableStringId;
    }

    public void setVariableStringId(String variableStringId) {
        this.variableStringId = variableStringId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        WebInputInspectorVariableKey that = (WebInputInspectorVariableKey) o;
        return Objects.equals(inspectorInfoLongId, that.inspectorInfoLongId)
                && Objects.equals(variableStringId, that.variableStringId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(inspectorInfoLongId);
        result = 31 * result + Objects.hashCode(variableStringId);
        return result;
    }

    @Override
    public String toString() {
        return "WebInputInspectorVariableKey{" +
                "inspectorInfoLongId=" + inspectorInfoLongId +
                ", variableStringId='" + variableStringId + '\'' +
                '}';
    }
}
