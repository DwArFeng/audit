package com.dwarfeng.audit.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 审计条目。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class FastJsonAuditEntry implements Bean {

    private static final long serialVersionUID = 4188976643443090756L;

    public static FastJsonAuditEntry of(AuditEntry auditEntry) {
        if (Objects.isNull(auditEntry)) {
            return null;
        } else {
            return new FastJsonAuditEntry(
                    FastJsonLongIdKey.of(auditEntry.getKey()),
                    FastJsonStringIdKey.of(auditEntry.getCategoryKey()),
                    auditEntry.getCreatedDate()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "category_key", ordinal = 2)
    private FastJsonStringIdKey categoryKey;

    @JSONField(name = "created_date", ordinal = 3)
    private Date createdDate;

    public FastJsonAuditEntry() {
    }

    public FastJsonAuditEntry(FastJsonLongIdKey key, FastJsonStringIdKey categoryKey, Date createdDate) {
        this.key = key;
        this.categoryKey = categoryKey;
        this.createdDate = createdDate;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
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
        return "FastJsonAuditEntry{" +
                "key=" + key +
                ", categoryKey=" + categoryKey +
                ", createdDate=" + createdDate +
                '}';
    }
}
