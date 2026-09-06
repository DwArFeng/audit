package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableInspectResult;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 审计器变量查看结果。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class JSFixedFastJsonInspectorVariableInspectResult implements Dto {

    private static final long serialVersionUID = 3513240994398522227L;

    public static JSFixedFastJsonInspectorVariableInspectResult of(
            InspectorVariableInspectResult inspectorVariableInspectResult
    ) {
        if (Objects.isNull(inspectorVariableInspectResult)) {
            return null;
        } else {
            return new JSFixedFastJsonInspectorVariableInspectResult(
                    inspectorVariableInspectResult.getValueType(), inspectorVariableInspectResult.getValue()
            );
        }
    }

    @JSONField(name = "value_type", ordinal = 1)
    private int valueType;

    @JSONField(name = "value", ordinal = 2, serialzeFeatures = SerializerFeature.WriteClassName)
    private Object value;

    public JSFixedFastJsonInspectorVariableInspectResult() {
    }

    public JSFixedFastJsonInspectorVariableInspectResult(int valueType, Object value) {
        this.valueType = valueType;
        this.value = value;
    }

    public int getValueType() {
        return valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonInspectorVariableInspectResult{" +
                "valueType=" + valueType +
                ", value=" + value +
                '}';
    }
}
