package com.dwarfeng.audit.stack.cache;

import com.dwarfeng.audit.stack.bean.entity.InspectionDriverInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 自动审计驱动器信息缓存。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionDriverInfoCache extends BatchBaseCache<LongIdKey, InspectionDriverInfo> {
}
