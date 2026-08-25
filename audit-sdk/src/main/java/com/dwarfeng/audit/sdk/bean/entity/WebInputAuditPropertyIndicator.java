package com.dwarfeng.audit.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.bean.key.WebInputAuditPropertyIndicatorKey;
import com.dwarfeng.audit.sdk.util.Constraints;
import com.dwarfeng.audit.sdk.util.ValidAuditPropertyType;
import com.dwarfeng.audit.stack.bean.entity.AuditPropertyIndicator;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

/**
 * WebInput 审计属性指示器。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class WebInputAuditPropertyIndicator implements Bean {

    private static final long serialVersionUID = 2715035567905674615L;

    public static AuditPropertyIndicator toStackBean(WebInputAuditPropertyIndicator webInputAuditPropertyIndicator) {
        if (Objects.isNull(webInputAuditPropertyIndicator)) {
            return null;
        } else {
            return new AuditPropertyIndicator(
                    WebInputAuditPropertyIndicatorKey.toStackBean(webInputAuditPropertyIndicator.getKey()),
                    webInputAuditPropertyIndicator.getLabel(),
                    webInputAuditPropertyIndicator.getPropertyType(),
                    webInputAuditPropertyIndicator.getDefaultStringValue(),
                    webInputAuditPropertyIndicator.getDefaultLongValue(),
                    webInputAuditPropertyIndicator.getDefaultDoubleValue(),
                    webInputAuditPropertyIndicator.getDefaultBooleanValue(),
                    webInputAuditPropertyIndicator.getDefaultDateValue(),
                    webInputAuditPropertyIndicator.getOrder()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    @NotNull
    private WebInputAuditPropertyIndicatorKey key;

    @JSONField(name = "label")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_LABEL)
    private String label;

    @JSONField(name = "property_type")
    @ValidAuditPropertyType
    private int propertyType;

    @JSONField(name = "default_string_value")
    private String defaultStringValue;

    @JSONField(name = "default_long_value")
    private Long defaultLongValue;

    @JSONField(name = "default_double_value")
    private Double defaultDoubleValue;

    @JSONField(name = "default_boolean_value")
    private Boolean defaultBooleanValue;

    @JSONField(name = "default_date_value")
    private Date defaultDateValue;

    @JSONField(name = "order")
    private int order;

    public WebInputAuditPropertyIndicator() {
    }

    public WebInputAuditPropertyIndicatorKey getKey() {
        return key;
    }

    public void setKey(WebInputAuditPropertyIndicatorKey key) {
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
        return "WebInputAuditPropertyIndicator{" +
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
