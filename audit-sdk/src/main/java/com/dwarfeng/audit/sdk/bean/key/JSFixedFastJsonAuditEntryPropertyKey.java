package com.dwarfeng.audit.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.audit.stack.bean.key.AuditEntryPropertyKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * JSFixed FastJson 审计条目属性主键。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class JSFixedFastJsonAuditEntryPropertyKey implements Key {

    private static final long serialVersionUID = 613918303323329301L;

    public static JSFixedFastJsonAuditEntryPropertyKey of(AuditEntryPropertyKey auditEntryPropertyKey) {
        if (Objects.isNull(auditEntryPropertyKey)) {
            return null;
        } else {
            return new JSFixedFastJsonAuditEntryPropertyKey(
                    auditEntryPropertyKey.getAuditEntryLongId(),
                    auditEntryPropertyKey.getPropertyStringId()
            );
        }
    }

    @JSONField(name = "audit_entry_long_id", ordinal = 1, serializeUsing = ToStringSerializer.class)
    private Long auditEntryLongId;

    @JSONField(name = "property_string_id", ordinal = 2)
    private String propertyStringId;

    public JSFixedFastJsonAuditEntryPropertyKey() {
    }

    public JSFixedFastJsonAuditEntryPropertyKey(Long auditEntryLongId, String propertyStringId) {
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

        JSFixedFastJsonAuditEntryPropertyKey that = (JSFixedFastJsonAuditEntryPropertyKey) o;
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
        return "JSFixedFastJsonAuditEntryPropertyKey{" +
                "auditEntryLongId=" + auditEntryLongId +
                ", propertyStringId='" + propertyStringId + '\'' +
                '}';
    }
}
