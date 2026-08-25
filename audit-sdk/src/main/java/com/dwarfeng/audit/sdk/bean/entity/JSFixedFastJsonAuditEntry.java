package com.dwarfeng.audit.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 审计条目。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class JSFixedFastJsonAuditEntry implements Bean {

    private static final long serialVersionUID = 642610257140791312L;

    public static JSFixedFastJsonAuditEntry of(AuditEntry auditEntry) {
        if (Objects.isNull(auditEntry)) {
            return null;
        } else {
            return new JSFixedFastJsonAuditEntry(
                    JSFixedFastJsonLongIdKey.of(auditEntry.getKey()),
                    FastJsonStringIdKey.of(auditEntry.getCategoryKey()),
                    auditEntry.getCreatedDate()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "category_key", ordinal = 2)
    private FastJsonStringIdKey categoryKey;

    @JSONField(name = "created_date", ordinal = 3)
    private Date createdDate;

    public JSFixedFastJsonAuditEntry() {
    }

    public JSFixedFastJsonAuditEntry(
            JSFixedFastJsonLongIdKey key, FastJsonStringIdKey categoryKey, Date createdDate
    ) {
        this.key = key;
        this.categoryKey = categoryKey;
        this.createdDate = createdDate;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public FastJsonStringIdKey getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(FastJsonStringIdKey categoryKey) {
        this.categoryKey = categoryKey;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonAuditEntry{" +
                "key=" + key +
                ", categoryKey=" + categoryKey +
                ", createdDate=" + createdDate +
                '}';
    }
}
