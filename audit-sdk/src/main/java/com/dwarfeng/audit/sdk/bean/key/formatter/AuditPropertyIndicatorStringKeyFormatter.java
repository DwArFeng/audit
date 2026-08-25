package com.dwarfeng.audit.sdk.bean.key.formatter;

import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.subgrade.sdk.common.Constants;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringKeyFormatter;

import java.util.Objects;

/**
 * AuditPropertyIndicatorKey 的文本格式化转换器。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class AuditPropertyIndicatorStringKeyFormatter implements StringKeyFormatter<AuditPropertyIndicatorKey> {

    private String prefix;

    public AuditPropertyIndicatorStringKeyFormatter(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String format(AuditPropertyIndicatorKey key) {
        Objects.requireNonNull(key);
        return prefix + key.getAuditCategoryStringId() + "_" + key.getPropertyStringId();
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
        return "AuditPropertyIndicatorStringKeyFormatter{" +
                "prefix='" + prefix + '\'' +
                '}';
    }
}
