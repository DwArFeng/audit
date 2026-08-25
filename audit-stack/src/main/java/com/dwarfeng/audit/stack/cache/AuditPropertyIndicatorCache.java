package com.dwarfeng.audit.stack.cache;

import com.dwarfeng.audit.stack.bean.entity.AuditPropertyIndicator;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 审计属性指示器缓存。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public interface AuditPropertyIndicatorCache extends BatchBaseCache<AuditPropertyIndicatorKey, AuditPropertyIndicator> {
}
