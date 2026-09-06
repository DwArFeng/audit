package com.dwarfeng.audit.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.key.InspectorVariableKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * FastJson 审计器变量键。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class FastJsonInspectorVariableKey implements Key {

    private static final long serialVersionUID = -4041696900547146342L;

    public static FastJsonInspectorVariableKey of(InspectorVariableKey inspectorVariableKey) {
        if (Objects.isNull(inspectorVariableKey)) {
            return null;
        } else {
            return new FastJsonInspectorVariableKey(
                    inspectorVariableKey.getInspectorInfoLongId(),
                    inspectorVariableKey.getVariableStringId()
            );
        }
    }

    @JSONField(name = "inspector_info_long_id", ordinal = 1)
    private Long inspectorInfoLongId;

    @JSONField(name = "variable_string_id", ordinal = 2)
    private String variableStringId;

    public FastJsonInspectorVariableKey() {
    }

    public FastJsonInspectorVariableKey(Long inspectorInfoLongId, String variableStringId) {
        this.inspectorInfoLongId = inspectorInfoLongId;
        this.variableStringId = variableStringId;
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

        FastJsonInspectorVariableKey that = (FastJsonInspectorVariableKey) o;
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
        return "FastJsonInspectorVariableKey{" +
                "inspectorInfoLongId=" + inspectorInfoLongId +
                ", variableStringId='" + variableStringId + '\'' +
                '}';
    }
}
