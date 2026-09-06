package com.dwarfeng.audit.stack.bean.entity;

import com.dwarfeng.audit.stack.bean.key.InspectorVariableKey;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;

import java.util.Date;

/**
 * 审计器变量。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectorVariable implements Entity<InspectorVariableKey> {

    private static final long serialVersionUID = -4626095653698789921L;

    private InspectorVariableKey key;
    /**
     * 变量类型。
     *
     * <p>
     * int 枚举，可能的状态为：
     * <ol>
     * <li>文本</li>
     * <li>整数</li>
     * <li>浮点数</li>
     * <li>布尔值</li>
     * <li>日期值</li>
     * </ol>
     * 详细值参考 sdk 模块的常量工具类。
     */
    private int valueType;

    private String stringValue;
    private Long longValue;
    private Double doubleValue;
    private Boolean booleanValue;
    private Date dateValue;

    public InspectorVariable() {
    }

    public InspectorVariable(
            InspectorVariableKey key, int valueType, String stringValue, Long longValue, Double doubleValue,
            Boolean booleanValue, Date dateValue
    ) {
        this.key = key;
        this.valueType = valueType;
        this.stringValue = stringValue;
        this.longValue = longValue;
        this.doubleValue = doubleValue;
        this.booleanValue = booleanValue;
        this.dateValue = dateValue;
    }

    @Override
    public InspectorVariableKey getKey() {
        return key;
    }

    @Override
    public void setKey(InspectorVariableKey key) {
        this.key = key;
    }

    public int getValueType() {
        return valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
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
        return "InspectorVariable{" +
                "key=" + key +
                ", valueType=" + valueType +
                ", stringValue='" + stringValue + '\'' +
                ", longValue=" + longValue +
                ", doubleValue=" + doubleValue +
                ", booleanValue=" + booleanValue +
                ", dateValue=" + dateValue +
                '}';
    }
}
