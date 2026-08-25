package com.dwarfeng.audit.stack.bean.entity;

import com.dwarfeng.audit.stack.bean.key.AuditEntryPropertyKey;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;

import java.util.Date;

/**
 * 审计条目属性。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class AuditEntryProperty implements Entity<AuditEntryPropertyKey> {

    private static final long serialVersionUID = 2544442743810455344L;

    private AuditEntryPropertyKey key;

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

    private String stringValue;
    private Long longValue;
    private Double doubleValue;
    private Boolean booleanValue;
    private Date dateValue;

    public AuditEntryProperty() {
    }

    public AuditEntryProperty(
            AuditEntryPropertyKey key, int propertyType, String stringValue, Long longValue, Double doubleValue,
            Boolean booleanValue, Date dateValue
    ) {
        this.key = key;
        this.propertyType = propertyType;
        this.stringValue = stringValue;
        this.longValue = longValue;
        this.doubleValue = doubleValue;
        this.booleanValue = booleanValue;
        this.dateValue = dateValue;
    }

    @Override
    public AuditEntryPropertyKey getKey() {
        return key;
    }

    @Override
    public void setKey(AuditEntryPropertyKey key) {
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
        return "AuditEntryProperty{" +
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
