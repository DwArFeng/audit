package com.dwarfeng.audit.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.util.Constraints;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 审计属性指示器主键。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class WebInputAuditPropertyIndicatorKey implements Key {

    private static final long serialVersionUID = -2416374166498663308L;

    public static AuditPropertyIndicatorKey toStackBean(
            WebInputAuditPropertyIndicatorKey webInputAuditPropertyIndicatorKey
    ) {
        if (Objects.isNull(webInputAuditPropertyIndicatorKey)) {
            return null;
        } else {
            return new AuditPropertyIndicatorKey(
                    webInputAuditPropertyIndicatorKey.getAuditCategoryStringId(),
                    webInputAuditPropertyIndicatorKey.getPropertyStringId()
            );
        }
    }

    @JSONField(name = "audit_category_string_id")
    @NotNull
    private String auditCategoryStringId;

    @JSONField(name = "property_string_id")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_PROPERTY_ID)
    private String propertyStringId;

    public WebInputAuditPropertyIndicatorKey() {
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

        WebInputAuditPropertyIndicatorKey that = (WebInputAuditPropertyIndicatorKey) o;
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
        return "WebInputAuditPropertyIndicatorKey{" +
                "auditCategoryStringId='" + auditCategoryStringId + '\'' +
                ", propertyStringId='" + propertyStringId + '\'' +
                '}';
    }
}
