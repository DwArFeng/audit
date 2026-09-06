package com.dwarfeng.audit.impl.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

public class HibernateInspectorVariableKey implements Key {

    private static final long serialVersionUID = -3554222130567262456L;

    private Long inspectorInfoLongId;
    private String variableStringId;

    public HibernateInspectorVariableKey() {
    }

    public HibernateInspectorVariableKey(Long inspectorInfoLongId, String variableStringId) {
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

        HibernateInspectorVariableKey that = (HibernateInspectorVariableKey) o;
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
        return "HibernateInspectorVariableKey{" +
                "inspectorInfoLongId=" + inspectorInfoLongId +
                ", variableStringId='" + variableStringId + '\'' +
                '}';
    }
}
