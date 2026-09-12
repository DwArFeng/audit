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
     * 值类型。
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
    private int valueType;

    /**
     * 值。
     *
     * <p>
     * 此处的值是一个对象，其类型由 {@link #valueType} 决定。其对应关系如下：
     * <table>
     *     <tr>
     *         <th>值类型</th>
     *         <th>值类型对应的对象类型</th>
     *     </tr>
     *     <tr>
     *         <td>字符串</td>
     *         <td>String</td>
     *     </tr>
     *     <tr>
     *         <td>整数</td>
     *         <td>Long</td>
     *     </tr>
     *     <tr>
     *         <td>浮点数</td>
     *         <td>Double</td>
     *     </tr>
     *     <tr>
     *         <td>布尔值</td>
     *         <td>Boolean</td>
     *     </tr>
     *     <tr>
     *         <td>日期值</td>
     *         <td>{@linkplain java.util.Date}</td>
     *     </tr>
     * </table>
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
