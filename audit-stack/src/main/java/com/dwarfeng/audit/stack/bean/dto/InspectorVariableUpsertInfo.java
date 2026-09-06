package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 审计器变量插入/更新信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectorVariableUpsertInfo implements Dto {

    private static final long serialVersionUID = -499265936171322777L;

    private LongIdKey inspectorInfoKey;
    private String inspectorVariableId;

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

    public InspectorVariableUpsertInfo() {
    }

    public InspectorVariableUpsertInfo(
            LongIdKey inspectorInfoKey, String inspectorVariableId, int valueType, Object value
    ) {
        this.inspectorInfoKey = inspectorInfoKey;
        this.inspectorVariableId = inspectorVariableId;
        this.valueType = valueType;
        this.value = value;
    }

    public LongIdKey getInspectorInfoKey() {
        return inspectorInfoKey;
    }

    public void setInspectorInfoKey(LongIdKey inspectorInfoKey) {
        this.inspectorInfoKey = inspectorInfoKey;
    }

    public String getInspectorVariableId() {
        return inspectorVariableId;
    }

    public void setInspectorVariableId(String inspectorVariableId) {
        this.inspectorVariableId = inspectorVariableId;
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
        return "InspectorVariableUpsertInfo{" +
                "inspectorInfoKey=" + inspectorInfoKey +
                ", inspectorVariableId='" + inspectorVariableId + '\'' +
                ", valueType=" + valueType +
                ", value=" + value +
                '}';
    }
}
