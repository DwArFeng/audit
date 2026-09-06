package com.dwarfeng.audit.stack.cache;

import com.dwarfeng.audit.stack.bean.entity.InspectorSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 审计器支持缓存。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectorSupportCache extends BatchBaseCache<StringIdKey, InspectorSupport> {
}
