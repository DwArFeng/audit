package com.dwarfeng.audit.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.util.Constraints;
import com.dwarfeng.audit.stack.bean.key.AuditEntryPropertyKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 审计条目属性主键。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class WebInputAuditEntryPropertyKey implements Key {

    private static final long serialVersionUID = -5964862402596467445L;

    public static AuditEntryPropertyKey toStackBean(WebInputAuditEntryPropertyKey webInputAuditEntryPropertyKey) {
        if (Objects.isNull(webInputAuditEntryPropertyKey)) {
            return null;
        } else {
            return new AuditEntryPropertyKey(
                    webInputAuditEntryPropertyKey.getAuditEntryLongId(),
                    webInputAuditEntryPropertyKey.getPropertyStringId()
            );
        }
    }

    @JSONField(name = "audit_entry_long_id")
    @NotNull
    private Long auditEntryLongId;

    @JSONField(name = "property_string_id")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_PROPERTY_ID)
    private String propertyStringId;

    public WebInputAuditEntryPropertyKey() {
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

        WebInputAuditEntryPropertyKey that = (WebInputAuditEntryPropertyKey) o;
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
        return "WebInputAuditEntryPropertyKey{" +
                "auditEntryLongId=" + auditEntryLongId +
                ", propertyStringId='" + propertyStringId + '\'' +
                '}';
    }
}
