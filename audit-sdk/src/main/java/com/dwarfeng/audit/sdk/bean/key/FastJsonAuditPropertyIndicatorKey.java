package com.dwarfeng.audit.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * FastJson 审计属性指示器主键。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class FastJsonAuditPropertyIndicatorKey implements Key {

    private static final long serialVersionUID = 8019317714978244453L;

    public static FastJsonAuditPropertyIndicatorKey of(AuditPropertyIndicatorKey auditPropertyIndicatorKey) {
        if (Objects.isNull(auditPropertyIndicatorKey)) {
            return null;
        } else {
            return new FastJsonAuditPropertyIndicatorKey(
                    auditPropertyIndicatorKey.getAuditCategoryStringId(),
                    auditPropertyIndicatorKey.getPropertyStringId()
            );
        }
    }

    @JSONField(name = "audit_category_string_id", ordinal = 1)
    private String auditCategoryStringId;

    @JSONField(name = "property_string_id", ordinal = 2)
    private String propertyStringId;

    public FastJsonAuditPropertyIndicatorKey() {
    }

    public FastJsonAuditPropertyIndicatorKey(String auditCategoryStringId, String propertyStringId) {
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

        FastJsonAuditPropertyIndicatorKey that = (FastJsonAuditPropertyIndicatorKey) o;
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
        return "FastJsonAuditPropertyIndicatorKey{" +
                "auditCategoryStringId='" + auditCategoryStringId + '\'' +
                ", propertyStringId='" + propertyStringId + '\'' +
                '}';
    }
}
