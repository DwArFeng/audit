package com.dwarfeng.audit.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Date;

/**
 * 审计条目。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class AuditEntry implements Entity<LongIdKey> {

    private static final long serialVersionUID = -6994315975926216980L;

    private LongIdKey key;
    private StringIdKey categoryKey;
    private Date createdDate;

    public AuditEntry() {
    }

    public AuditEntry(LongIdKey key, StringIdKey categoryKey, Date createdDate) {
        this.key = key;
        this.categoryKey = categoryKey;
        this.createdDate = createdDate;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public StringIdKey getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(StringIdKey categoryKey) {
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
        return "AuditEntry{" +
                "key=" + key +
                ", categoryKey=" + categoryKey +
                ", createdDate=" + createdDate +
                '}';
    }
}
