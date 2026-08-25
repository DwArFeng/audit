package com.dwarfeng.audit.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.audit.sdk.bean.key.JSFixedFastJsonAuditEntryPropertyKey;
import com.dwarfeng.audit.stack.bean.entity.AuditEntryProperty;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 审计条目属性。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class JSFixedFastJsonAuditEntryProperty implements Bean {

    private static final long serialVersionUID = -5705050622394344319L;

    public static JSFixedFastJsonAuditEntryProperty of(AuditEntryProperty auditEntryProperty) {
        if (Objects.isNull(auditEntryProperty)) {
            return null;
        } else {
            return new JSFixedFastJsonAuditEntryProperty(
                    JSFixedFastJsonAuditEntryPropertyKey.of(auditEntryProperty.getKey()),
                    auditEntryProperty.getPropertyType(),
                    auditEntryProperty.getStringValue(),
                    auditEntryProperty.getLongValue(),
                    auditEntryProperty.getDoubleValue(),
                    auditEntryProperty.getBooleanValue(),
                    auditEntryProperty.getDateValue()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonAuditEntryPropertyKey key;

    @JSONField(name = "property_type", ordinal = 2)
    private int propertyType;

    @JSONField(name = "string_value", ordinal = 3)
    private String stringValue;

    @JSONField(name = "long_value", ordinal = 4, serializeUsing = ToStringSerializer.class)
    private Long longValue;

    @JSONField(name = "double_value", ordinal = 5)
    private Double doubleValue;

    @JSONField(name = "boolean_value", ordinal = 6)
    private Boolean booleanValue;

    @JSONField(name = "date_value", ordinal = 7)
    private Date dateValue;

    public JSFixedFastJsonAuditEntryProperty() {
    }

    public JSFixedFastJsonAuditEntryProperty(
            JSFixedFastJsonAuditEntryPropertyKey key, int propertyType, String stringValue, Long longValue,
            Double doubleValue, Boolean booleanValue, Date dateValue
    ) {
        this.key = key;
        this.propertyType = propertyType;
        this.stringValue = stringValue;
        this.longValue = longValue;
        this.doubleValue = doubleValue;
        this.booleanValue = booleanValue;
        this.dateValue = dateValue;
    }

    public JSFixedFastJsonAuditEntryPropertyKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonAuditEntryPropertyKey key) {
        this.key = key;
    }

    public int getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(int propertyType) {
        this.propertyType = propertyType;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Long getLongValue() {
        return longValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonAuditEntryProperty{" +
                "key=" + key +
                ", propertyType=" + propertyType +
                ", stringValue='" + stringValue + '\'' +
                ", longValue=" + longValue +
                ", doubleValue=" + doubleValue +
                ", booleanValue=" + booleanValue +
                ", dateValue=" + dateValue +
                '}';
    }
}
