package com.dwarfeng.audit.stack.cache;

import com.dwarfeng.audit.stack.bean.entity.InspectorVariable;
import com.dwarfeng.audit.stack.bean.key.InspectorVariableKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 审计器变量缓存。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectorVariableCache extends BatchBaseCache<InspectorVariableKey, InspectorVariable> {
}
