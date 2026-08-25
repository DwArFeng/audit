package com.dwarfeng.audit.impl.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

public class HibernateAuditEntryPropertyKey implements Key {

    private static final long serialVersionUID = -4654030842776590582L;

    private Long auditEntryLongId;
    private String propertyStringId;

    public HibernateAuditEntryPropertyKey() {
    }

    public HibernateAuditEntryPropertyKey(Long auditEntryLongId, String propertyStringId) {
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

        HibernateAuditEntryPropertyKey that = (HibernateAuditEntryPropertyKey) o;
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
        return "HibernateAuditEntryPropertyKey{" +
                "auditEntryLongId=" + auditEntryLongId +
                ", propertyStringId='" + propertyStringId + '\'' +
                '}';
    }
}
