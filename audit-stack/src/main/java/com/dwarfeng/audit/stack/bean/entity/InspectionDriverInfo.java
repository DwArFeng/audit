package com.dwarfeng.audit.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 自动审计驱动器信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionDriverInfo implements Entity<LongIdKey> {

    private static final long serialVersionUID = 2094652084017741851L;

    private LongIdKey key;
    private LongIdKey inspectionKey;
    private boolean enabled;
    private String type;
    private String param;
    private String remark;

    public InspectionDriverInfo() {
    }

    public InspectionDriverInfo(
            LongIdKey key, LongIdKey inspectionKey, boolean enabled, String type, String param, String remark
    ) {
        this.key = key;
        this.inspectionKey = inspectionKey;
        this.enabled = enabled;
        this.type = type;
        this.param = param;
        this.remark = remark;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getInspectionKey() {
        return inspectionKey;
    }

    public void setInspectionKey(LongIdKey inspectionKey) {
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
        return "InspectionDriverInfo{" +
                "key=" + key +
                ", inspectionKey=" + inspectionKey +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
