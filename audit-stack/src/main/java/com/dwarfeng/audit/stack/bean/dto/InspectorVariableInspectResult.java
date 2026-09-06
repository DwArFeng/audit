package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 审计器变量查看结果。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectorVariableInspectResult implements Dto {

    private static final long serialVersionUID = 7548231528005299798L;

    /**
     * 审计器变量值类型。
     *
     * <p>
     * int 枚举，可能的状态为：文本、整数、浮点数、布尔值、日期值。<br>
     * 详细值参考 sdk 模块的常量工具类。
     */
    private int valueType;

    /**
     * 审计器变量值。
     *
     * <p>
     * 该字段的具体类型取决于 <code>valueType</code> 字段的值，依次为
     * String、Long、Double、Boolean、Date。
     */
    private Object value;

    public InspectorVariableInspectResult() {
    }

    public InspectorVariableInspectResult(int valueType, Object value) {
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
        return "InspectorVariableInspectResult{" +
                "valueType=" + valueType +
                ", value=" + value +
                '}';
    }
}
