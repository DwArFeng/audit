package com.dwarfeng.audit.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * JSFixed FastJson 审计类别。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class JSFixedFastJsonAuditCategory implements Bean {

    private static final long serialVersionUID = -2774802838783046833L;

    public static JSFixedFastJsonAuditCategory of(AuditCategory auditCategory) {
        if (Objects.isNull(auditCategory)) {
            return null;
        } else {
            return new JSFixedFastJsonAuditCategory(
                    FastJsonStringIdKey.of(auditCategory.getKey()),
                    auditCategory.isEnabled(),
                    auditCategory.getName(),
                    auditCategory.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "enabled", ordinal = 2)
    private boolean enabled;

    @JSONField(name = "name", ordinal = 3)
    private String name;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    public JSFixedFastJsonAuditCategory() {
    }

    public JSFixedFastJsonAuditCategory(FastJsonStringIdKey key, boolean enabled, String name, String remark) {
        this.key = key;
        this.enabled = enabled;
        this.name = name;
        this.remark = remark;
    }

    public FastJsonStringIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonStringIdKey key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonAuditCategory{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
