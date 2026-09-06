package com.dwarfeng.audit.stack.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * 审计器变量键。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectorVariableKey implements Key {

    private static final long serialVersionUID = -1137594550524337084L;

    private Long inspectorInfoLongId;
    private String variableStringId;

    public InspectorVariableKey() {
    }

    public InspectorVariableKey(Long inspectorInfoLongId, String variableStringId) {
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

        InspectorVariableKey that = (InspectorVariableKey) o;
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
        return "InspectorVariableKey{" +
                "inspectorInfoLongId=" + inspectorInfoLongId +
                ", variableStringId='" + variableStringId + '\'' +
                '}';
    }
}
