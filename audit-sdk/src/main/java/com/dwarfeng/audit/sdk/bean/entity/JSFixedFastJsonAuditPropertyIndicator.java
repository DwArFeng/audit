package com.dwarfeng.audit.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.audit.sdk.bean.key.JSFixedFastJsonAuditPropertyIndicatorKey;
import com.dwarfeng.audit.stack.bean.entity.AuditPropertyIndicator;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 审计属性指示器。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class JSFixedFastJsonAuditPropertyIndicator implements Bean {

    private static final long serialVersionUID = -4994484918363976857L;

    public static JSFixedFastJsonAuditPropertyIndicator of(AuditPropertyIndicator auditPropertyIndicator) {
        if (Objects.isNull(auditPropertyIndicator)) {
            return null;
        } else {
            return new JSFixedFastJsonAuditPropertyIndicator(
                    JSFixedFastJsonAuditPropertyIndicatorKey.of(auditPropertyIndicator.getKey()),
                    auditPropertyIndicator.getLabel(),
                    auditPropertyIndicator.getPropertyType(),
                    auditPropertyIndicator.getDefaultStringValue(),
                    auditPropertyIndicator.getDefaultLongValue(),
                    auditPropertyIndicator.getDefaultDoubleValue(),
                    auditPropertyIndicator.getDefaultBooleanValue(),
                    auditPropertyIndicator.getDefaultDateValue(),
                    auditPropertyIndicator.getOrder()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonAuditPropertyIndicatorKey key;

    @JSONField(name = "label", ordinal = 2)
    private String label;

    @JSONField(name = "property_type", ordinal = 3)
    private int propertyType;

    @JSONField(name = "default_string_value", ordinal = 4)
    private String defaultStringValue;

    @JSONField(name = "default_long_value", ordinal = 5, serializeUsing = ToStringSerializer.class)
    private Long defaultLongValue;

    @JSONField(name = "default_double_value", ordinal = 6)
    private Double defaultDoubleValue;

    @JSONField(name = "default_boolean_value", ordinal = 7)
    private Boolean defaultBooleanValue;

    @JSONField(name = "default_date_value", ordinal = 8)
    private Date defaultDateValue;

    @JSONField(name = "order", ordinal = 9)
    private int order;

    public JSFixedFastJsonAuditPropertyIndicator() {
    }

    public JSFixedFastJsonAuditPropertyIndicator(
            JSFixedFastJsonAuditPropertyIndicatorKey key, String label, int propertyType, String defaultStringValue,
            Long defaultLongValue, Double defaultDoubleValue, Boolean defaultBooleanValue, Date defaultDateValue,
            int order
    ) {
        this.key = key;
        this.label = label;
        this.propertyType = propertyType;
        this.defaultStringValue = defaultStringValue;
        this.defaultLongValue = defaultLongValue;
        this.defaultDoubleValue = defaultDoubleValue;
        this.defaultBooleanValue = defaultBooleanValue;
        this.defaultDateValue = defaultDateValue;
        this.order = order;
    }

    public JSFixedFastJsonAuditPropertyIndicatorKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonAuditPropertyIndicatorKey key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(int propertyType) {
        this.propertyType = propertyType;
    }

    public String getDefaultStringValue() {
        return defaultStringValue;
    }

    public void setDefaultStringValue(String defaultStringValue) {
        this.defaultStringValue = defaultStringValue;
    }

    public Long getDefaultLongValue() {
        return defaultLongValue;
    }

    public void setDefaultLongValue(Long defaultLongValue) {
        this.defaultLongValue = defaultLongValue;
    }

    public Double getDefaultDoubleValue() {
        return defaultDoubleValue;
    }

    public void setDefaultDoubleValue(Double defaultDoubleValue) {
        this.defaultDoubleValue = defaultDoubleValue;
    }

    public Boolean getDefaultBooleanValue() {
        return defaultBooleanValue;
    }

    public void setDefaultBooleanValue(Boolean defaultBooleanValue) {
        this.defaultBooleanValue = defaultBooleanValue;
    }

    public Date getDefaultDateValue() {
        return defaultDateValue;
    }

    public void setDefaultDateValue(Date defaultDateValue) {
        this.defaultDateValue = defaultDateValue;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonAuditPropertyIndicator{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", propertyType=" + propertyType +
                ", defaultStringValue='" + defaultStringValue + '\'' +
                ", defaultLongValue=" + defaultLongValue +
                ", defaultDoubleValue=" + defaultDoubleValue +
                ", defaultBooleanValue=" + defaultBooleanValue +
                ", defaultDateValue=" + defaultDateValue +
                ", order=" + order +
                '}';
    }
}
