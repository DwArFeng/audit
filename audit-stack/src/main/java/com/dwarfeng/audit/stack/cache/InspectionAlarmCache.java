package com.dwarfeng.audit.stack.cache;

import com.dwarfeng.audit.stack.bean.entity.InspectionAlarm;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 自动审计报警。自动审计报警是不可变的历史事件缓存。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionAlarmCache extends BatchBaseCache<LongIdKey, InspectionAlarm> {
}
