package com.dwarfeng.audit.stack.cache;

import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 审计类别缓存。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public interface AuditCategoryCache extends BatchBaseCache<StringIdKey, AuditCategory> {
}
