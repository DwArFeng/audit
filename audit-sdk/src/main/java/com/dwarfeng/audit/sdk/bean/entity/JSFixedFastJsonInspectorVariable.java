package com.dwarfeng.audit.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.audit.sdk.bean.key.JSFixedFastJsonInspectorVariableKey;
import com.dwarfeng.audit.stack.bean.entity.InspectorVariable;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 审计器变量。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class JSFixedFastJsonInspectorVariable implements Bean {

    private static final long serialVersionUID = 2709487055136853355L;

    public static JSFixedFastJsonInspectorVariable of(InspectorVariable inspectorVariable) {
        if (Objects.isNull(inspectorVariable)) {
            return null;
        } else {
            return new JSFixedFastJsonInspectorVariable(
                    JSFixedFastJsonInspectorVariableKey.of(inspectorVariable.getKey()),
                    inspectorVariable.getValueType(),
                    inspectorVariable.getStringValue(),
                    inspectorVariable.getLongValue(),
                    inspectorVariable.getDoubleValue(),
                    inspectorVariable.getBooleanValue(),
                    inspectorVariable.getDateValue()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonInspectorVariableKey key;

    @JSONField(name = "value_type", ordinal = 2)
    private int valueType;

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

    public JSFixedFastJsonInspectorVariable() {
    }

    public JSFixedFastJsonInspectorVariable(
            JSFixedFastJsonInspectorVariableKey key, int valueType, String stringValue, Long longValue,
            Double doubleValue, Boolean booleanValue, Date dateValue
    ) {
        this.key = key;
        this.valueType = valueType;
        this.stringValue = stringValue;
        this.longValue = longValue;
        this.doubleValue = doubleValue;
        this.booleanValue = booleanValue;
        this.dateValue = dateValue;
    }

    public JSFixedFastJsonInspectorVariableKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonInspectorVariableKey key) {
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
        return "JSFixedFastJsonInspectorVariable{" +
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
