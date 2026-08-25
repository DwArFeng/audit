package com.dwarfeng.audit.stack.cache;

import com.dwarfeng.audit.stack.bean.entity.AuditEntryProperty;
import com.dwarfeng.audit.stack.bean.key.AuditEntryPropertyKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 审计条目属性缓存。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public interface AuditEntryPropertyCache extends BatchBaseCache<AuditEntryPropertyKey, AuditEntryProperty> {
}
