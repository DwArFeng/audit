package com.dwarfeng.audit.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.util.Constraints;
import com.dwarfeng.audit.stack.bean.entity.InspectorInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

/**
 * WebInput 审计器信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class WebInputInspectorInfo implements Bean {

    private static final long serialVersionUID = -1532796002079250060L;

    public static InspectorInfo toStackBean(WebInputInspectorInfo webInputInspectorInfo) {
        if (Objects.isNull(webInputInspectorInfo)) {
            return null;
        } else {
            return new InspectorInfo(
                    WebInputLongIdKey.toStackBean(webInputInspectorInfo.getKey()),
                    WebInputLongIdKey.toStackBean(webInputInspectorInfo.getInspectionKey()),
                    webInputInspectorInfo.getIndex(),
                    webInputInspectorInfo.isEnabled(),
                    webInputInspectorInfo.getType(),
                    webInputInspectorInfo.getParam(),
                    webInputInspectorInfo.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    private WebInputLongIdKey key;

    @JSONField(name = "inspection_key")
    @Valid
    private WebInputLongIdKey inspectionKey;

    @JSONField(name = "index")
    @PositiveOrZero
    private int index;

    @JSONField(name = "enabled")
    private boolean enabled;

    @JSONField(name = "type")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_TYPE)
    private String type;

    @JSONField(name = "param")
    private String param;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputInspectorInfo() {
    }

    public WebInputLongIdKey getKey() {
        return key;
    }

    public void setKey(WebInputLongIdKey key) {
        this.key = key;
    }

    public WebInputLongIdKey getInspectionKey() {
        return inspectionKey;
    }

    public void setInspectionKey(WebInputLongIdKey inspectionKey) {
        this.inspectionKey = inspectionKey;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "WebInputInspectorInfo{" +
                "key=" + key +
                ", inspectionKey=" + inspectionKey +
                ", index=" + index +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
