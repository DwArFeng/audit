package com.dwarfeng.audit.stack.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * 审计条目属性主键。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class AuditEntryPropertyKey implements Key {

    private static final long serialVersionUID = 3686216745388964940L;

    private Long auditEntryLongId;
    private String propertyStringId;

    public AuditEntryPropertyKey() {
    }

    public AuditEntryPropertyKey(Long auditEntryLongId, String propertyStringId) {
        this.auditEntryLongId = auditEntryLongId;
        this.propertyStringId = propertyStringId;
    }

    public Long getAuditEntryLongId() {
        return auditEntryLongId;
    }

    public void setAuditEntryLongId(Long auditEntryLongId) {
        this.auditEntryLongId = auditEntryLongId;
    }

    public String getPropertyStringId() {
        return propertyStringId;
    }

    public void setPropertyStringId(String propertyStringId) {
        this.propertyStringId = propertyStringId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        AuditEntryPropertyKey that = (AuditEntryPropertyKey) o;
        return Objects.equals(auditEntryLongId, that.auditEntryLongId)
                && Objects.equals(propertyStringId, that.propertyStringId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(auditEntryLongId);
        result = 31 * result + Objects.hashCode(propertyStringId);
        return result;
    }

    @Override
    public String toString() {
        return "AuditEntryPropertyKey{" +
                "auditEntryLongId=" + auditEntryLongId +
                ", propertyStringId='" + propertyStringId + '\'' +
                '}';
    }
}
