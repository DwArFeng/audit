package com.dwarfeng.audit.stack.bean.entity;

import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;

import java.util.Date;

/**
 * 审计属性指示器。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class AuditPropertyIndicator implements Entity<AuditPropertyIndicatorKey> {

    private static final long serialVersionUID = 931873241163915740L;

    private AuditPropertyIndicatorKey key;
    private String label;

    /**
     * 属性类型。
     *
     * <p>
     * int 枚举，可能的状态为：
     * <ul>
     *     <li>字符串</li>
     *     <li>整数</li>
     *     <li>浮点数</li>
     *     <li>布尔值</li>
     *     <li>日期值</li>
     * </ul>
     * 详细值参考 sdk 模块的常量工具类。
     */
    private int propertyType;

    private String defaultStringValue;
    private Long defaultLongValue;
    private Double defaultDoubleValue;
    private Boolean defaultBooleanValue;
    private Date defaultDateValue;
    private int order;

    public AuditPropertyIndicator() {
    }

    public AuditPropertyIndicator(
            AuditPropertyIndicatorKey key, String label, int propertyType, String defaultStringValue,
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

    @Override
    public AuditPropertyIndicatorKey getKey() {
        return key;
    }

    @Override
    public void setKey(AuditPropertyIndicatorKey key) {
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
        return "AuditPropertyIndicator{" +
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
