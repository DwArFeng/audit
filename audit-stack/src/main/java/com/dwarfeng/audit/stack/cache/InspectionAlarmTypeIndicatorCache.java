package com.dwarfeng.audit.stack.cache;

import com.dwarfeng.audit.stack.bean.entity.InspectionAlarmTypeIndicator;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 自动审计报警类型指示器缓存。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionAlarmTypeIndicatorCache extends BatchBaseCache<StringIdKey, InspectionAlarmTypeIndicator> {
}
