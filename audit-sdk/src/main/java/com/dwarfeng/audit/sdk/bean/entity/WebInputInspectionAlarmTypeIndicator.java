package com.dwarfeng.audit.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.util.Constraints;
import com.dwarfeng.audit.stack.bean.entity.InspectionAlarmTypeIndicator;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 自动审计报警类型指示器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class WebInputInspectionAlarmTypeIndicator implements Bean {

    private static final long serialVersionUID = -1839724283700616159L;

    public static InspectionAlarmTypeIndicator toStackBean(
            WebInputInspectionAlarmTypeIndicator webInputInspectionAlarmTypeIndicator
    ) {
        if (Objects.isNull(webInputInspectionAlarmTypeIndicator)) {
            return null;
        } else {
            return new InspectionAlarmTypeIndicator(
                    WebInputStringIdKey.toStackBean(webInputInspectionAlarmTypeIndicator.getKey()),
                    webInputInspectionAlarmTypeIndicator.getLabel(),
                    webInputInspectionAlarmTypeIndicator.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @NotNull
    @Valid
    private WebInputStringIdKey key;

    @JSONField(name = "label")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_LABEL)
    private String label;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputInspectionAlarmTypeIndicator() {
    }

    public WebInputStringIdKey getKey() {
        return key;
    }

    public void setKey(WebInputStringIdKey key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "WebInputInspectionAlarmTypeIndicator{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
