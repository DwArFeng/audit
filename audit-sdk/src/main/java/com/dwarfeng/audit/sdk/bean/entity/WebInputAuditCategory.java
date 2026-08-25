package com.dwarfeng.audit.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.util.Constraints;
import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 审计类别。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class WebInputAuditCategory implements Bean {

    private static final long serialVersionUID = -5937827870616569088L;

    public static AuditCategory toStackBean(WebInputAuditCategory webInputAuditCategory) {
        if (Objects.isNull(webInputAuditCategory)) {
            return null;
        } else {
            return new AuditCategory(
                    WebInputStringIdKey.toStackBean(webInputAuditCategory.getKey()),
                    webInputAuditCategory.isEnabled(),
                    webInputAuditCategory.getName(),
                    webInputAuditCategory.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    @NotNull
    private WebInputStringIdKey key;

    @JSONField(name = "enabled")
    private boolean enabled;

    @JSONField(name = "name")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_NAME)
    private String name;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputAuditCategory() {
    }

    public WebInputStringIdKey getKey() {
        return key;
    }

    public void setKey(WebInputStringIdKey key) {
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
        return "WebInputAuditCategory{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
