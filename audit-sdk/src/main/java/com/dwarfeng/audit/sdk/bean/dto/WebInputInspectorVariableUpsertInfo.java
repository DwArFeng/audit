package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.util.Constraints;
import com.dwarfeng.audit.sdk.util.ValidInspectorVariableValueType;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableUpsertInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 审计器变量插入/更新信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class WebInputInspectorVariableUpsertInfo implements Bean {

    private static final long serialVersionUID = 8102450797985321895L;

    public static InspectorVariableUpsertInfo toStackBean(
            WebInputInspectorVariableUpsertInfo webInputInspectorVariableUpsertInfo
    ) {
        if (Objects.isNull(webInputInspectorVariableUpsertInfo)) {
            return null;
        } else {
            return new InspectorVariableUpsertInfo(
                    WebInputLongIdKey.toStackBean(webInputInspectorVariableUpsertInfo.getInspectorInfoKey()),
                    webInputInspectorVariableUpsertInfo.getInspectorVariableId(),
                    webInputInspectorVariableUpsertInfo.getValueType(),
                    webInputInspectorVariableUpsertInfo.getValue()
            );
        }
    }

    @JSONField(name = "inspectorInfoKey")
    @Valid
    @NotNull
    private WebInputLongIdKey inspectorInfoKey;

    @JSONField(name = "inspectorVariableId")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_STRING_ID)
    private String inspectorVariableId;

    @JSONField(name = "valueType")
    @ValidInspectorVariableValueType
    private int valueType;

    /**
     * 审计器变量值。
     *
     * <p>
     * 该字段的具体类型取决于 <code>valueType</code> 字段的值，依次为
     * String、Long、Double、Boolean、Date。
     */
    @JSONField(name = "value")
    private Object value;

    public WebInputInspectorVariableUpsertInfo() {
    }

    public WebInputLongIdKey getInspectorInfoKey() {
        return inspectorInfoKey;
    }

    public void setInspectorInfoKey(WebInputLongIdKey inspectorInfoKey) {
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
        return "WebInputInspectorVariableUpsertInfo{" +
                "inspectorInfoKey=" + inspectorInfoKey +
                ", inspectorVariableId='" + inspectorVariableId + '\'' +
                ", valueType=" + valueType +
                ", value=" + value +
                '}';
    }
}
