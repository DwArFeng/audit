package com.dwarfeng.audit.stack.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * 审计属性指示器主键。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class AuditPropertyIndicatorKey implements Key {

    private static final long serialVersionUID = -9128922780885301700L;

    private String auditCategoryStringId;
    private String propertyStringId;

    public AuditPropertyIndicatorKey() {
    }

    public AuditPropertyIndicatorKey(String auditCategoryStringId, String propertyStringId) {
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

        AuditPropertyIndicatorKey that = (AuditPropertyIndicatorKey) o;
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
        return "AuditPropertyIndicatorKey{" +
                "auditCategoryStringId='" + auditCategoryStringId + '\'' +
                ", propertyStringId='" + propertyStringId + '\'' +
                '}';
    }
}
