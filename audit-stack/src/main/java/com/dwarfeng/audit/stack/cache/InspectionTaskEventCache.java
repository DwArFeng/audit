package com.dwarfeng.audit.stack.cache;

import com.dwarfeng.audit.stack.bean.entity.InspectionTaskEvent;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 自动审计任务事件缓存。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionTaskEventCache extends BatchBaseCache<LongIdKey, InspectionTaskEvent> {
}
