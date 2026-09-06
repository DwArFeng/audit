package com.dwarfeng.audit.sdk.bean.key.formatter;

import com.dwarfeng.audit.stack.bean.key.InspectorVariableKey;
import com.dwarfeng.subgrade.sdk.common.Constants;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringKeyFormatter;

import java.util.Objects;

/**
 * InspectorVariableKey 的文本格式化转换器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectorVariableStringKeyFormatter implements StringKeyFormatter<InspectorVariableKey> {

    private String prefix;

    public InspectorVariableStringKeyFormatter(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String format(InspectorVariableKey key) {
        Objects.requireNonNull(key);
        return prefix + key.getInspectorInfoLongId() + "_" + key.getVariableStringId();
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
        return "InspectorVariableStringKeyFormatter{" +
                "prefix='" + prefix + '\'' +
                '}';
    }
}
