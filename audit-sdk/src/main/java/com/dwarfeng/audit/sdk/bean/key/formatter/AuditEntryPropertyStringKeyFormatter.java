package com.dwarfeng.audit.sdk.bean.key.formatter;

import com.dwarfeng.audit.stack.bean.key.AuditEntryPropertyKey;
import com.dwarfeng.subgrade.sdk.common.Constants;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringKeyFormatter;

import java.util.Objects;

/**
 * AuditEntryPropertyKey 的文本格式化转换器。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class AuditEntryPropertyStringKeyFormatter implements StringKeyFormatter<AuditEntryPropertyKey> {

    private String prefix;

    public AuditEntryPropertyStringKeyFormatter(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String format(AuditEntryPropertyKey key) {
        Objects.requireNonNull(key);
        return prefix + key.getAuditEntryLongId() + "_" + key.getPropertyStringId();
    }

    @Override
    public String generalFormat() {
        return prefix + Constants.REDIS_KEY_WILDCARD_CHARACTER;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return "AuditEntryPropertyStringKeyFormatter{" +
                "prefix='" + prefix + '\'' +
                '}';
    }
}
