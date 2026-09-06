package com.dwarfeng.audit.stack.cache;

import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 自动审计任务缓存。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionTaskCache extends BatchBaseCache<LongIdKey, InspectionTask> {
}
