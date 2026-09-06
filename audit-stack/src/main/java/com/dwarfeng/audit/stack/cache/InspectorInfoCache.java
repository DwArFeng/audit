package com.dwarfeng.audit.stack.cache;

import com.dwarfeng.audit.stack.bean.entity.InspectorInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 审计器信息缓存。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectorInfoCache extends BatchBaseCache<LongIdKey, InspectorInfo> {
}
