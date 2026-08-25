package com.dwarfeng.audit.impl.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

public class HibernateAuditPropertyIndicatorKey implements Key {

    private static final long serialVersionUID = 1787655985556329075L;

    private String auditCategoryStringId;
    private String propertyStringId;

    public HibernateAuditPropertyIndicatorKey() {
    }

    public HibernateAuditPropertyIndicatorKey(String auditCategoryStringId, String propertyStringId) {
        this.auditCategoryStringId = auditCategoryStringId;
        this.propertyStringId = propertyStringId;
    }

    public String getAuditCategoryStringId() {
        return auditCategoryStringId;
    }

    public void setAuditCategoryStringId(String auditCategoryStringId) {
        this.auditCategoryStringId = auditCategoryStringId;
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

        HibernateAuditPropertyIndicatorKey that = (HibernateAuditPropertyIndicatorKey) o;
        return Objects.equals(auditCategoryStringId, that.auditCategoryStringId)
                && Objects.equals(propertyStringId, that.propertyStringId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(auditCategoryStringId);
        result = 31 * result + Objects.hashCode(propertyStringId);
        return result;
    }

    @Override
    public String toString() {
        return "HibernateAuditPropertyIndicatorKey{" +
                "auditCategoryStringId='" + auditCategoryStringId + '\'' +
                ", propertyStringId='" + propertyStringId + '\'' +
                '}';
    }
}
