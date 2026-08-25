package com.dwarfeng.audit.stack.cache;

import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 审计条目缓存。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public interface AuditEntryCache extends BatchBaseCache<LongIdKey, AuditEntry> {
}
