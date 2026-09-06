package com.dwarfeng.audit.stack.cache;

import com.dwarfeng.audit.stack.bean.entity.InspectionDriverSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 自动审计驱动器支持缓存。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionDriverSupportCache extends BatchBaseCache<StringIdKey, InspectionDriverSupport> {
}
