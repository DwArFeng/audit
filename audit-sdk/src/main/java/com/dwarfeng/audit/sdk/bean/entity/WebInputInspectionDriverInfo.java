package com.dwarfeng.audit.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.util.Constraints;
import com.dwarfeng.audit.stack.bean.entity.InspectionDriverInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 自动审计驱动器信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class WebInputInspectionDriverInfo implements Bean {

    private static final long serialVersionUID = -1190673005177327997L;

    public static InspectionDriverInfo toStackBean(WebInputInspectionDriverInfo webInputInspectionDriverInfo) {
        if (Objects.isNull(webInputInspectionDriverInfo)) {
            return null;
        } else {
            return new InspectionDriverInfo(
                    WebInputLongIdKey.toStackBean(webInputInspectionDriverInfo.getKey()),
                    WebInputLongIdKey.toStackBean(webInputInspectionDriverInfo.getInspectionKey()),
                    webInputInspectionDriverInfo.isEnabled(),
                    webInputInspectionDriverInfo.getType(),
                    webInputInspectionDriverInfo.getParam(),
                    webInputInspectionDriverInfo.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    private WebInputLongIdKey key;

    @JSONField(name = "inspection_key")
    @Valid
    private WebInputLongIdKey inspectionKey;

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

    public WebInputInspectionDriverInfo() {
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
        return "WebInputInspectionDriverInfo{" +
                "key=" + key +
                ", inspectionKey=" + inspectionKey +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
