package com.dwarfeng.audit.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.entity.InspectionDriverInfo;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * JSFixed FastJson 自动审计驱动器信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class JSFixedFastJsonInspectionDriverInfo implements Bean {

    private static final long serialVersionUID = -3827103064517346146L;

    public static JSFixedFastJsonInspectionDriverInfo of(InspectionDriverInfo inspectionDriverInfo) {
        if (Objects.isNull(inspectionDriverInfo)) {
            return null;
        } else {
            return new JSFixedFastJsonInspectionDriverInfo(
                    JSFixedFastJsonLongIdKey.of(inspectionDriverInfo.getKey()),
                    JSFixedFastJsonLongIdKey.of(inspectionDriverInfo.getInspectionKey()),
                    inspectionDriverInfo.isEnabled(),
                    inspectionDriverInfo.getType(),
                    inspectionDriverInfo.getParam(),
                    inspectionDriverInfo.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "inspection_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey inspectionKey;

    @JSONField(name = "enabled", ordinal = 3)
    private boolean enabled;

    @JSONField(name = "type", ordinal = 4)
    private String type;

    @JSONField(name = "param", ordinal = 5)
    private String param;

    @JSONField(name = "remark", ordinal = 6)
    private String remark;

    public JSFixedFastJsonInspectionDriverInfo() {
    }

    public JSFixedFastJsonInspectionDriverInfo(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey inspectionKey, boolean enabled, String type,
            String param, String remark
    ) {
        this.key = key;
        this.inspectionKey = inspectionKey;
        this.enabled = enabled;
        this.type = type;
        this.param = param;
        this.remark = remark;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getInspectionKey() {
        return inspectionKey;
    }

    public void setInspectionKey(JSFixedFastJsonLongIdKey inspectionKey) {
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
        return "JSFixedFastJsonInspectionDriverInfo{" +
                "key=" + key +
                ", inspectionKey=" + inspectionKey +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
