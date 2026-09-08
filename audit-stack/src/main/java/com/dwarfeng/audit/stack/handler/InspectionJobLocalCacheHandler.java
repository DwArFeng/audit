package com.dwarfeng.audit.stack.handler;

import com.dwarfeng.audit.stack.struct.InspectionJobLocalCache;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.handler.LocalCacheHandler;

/**
 * 自动审计作业本地缓存处理器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionJobLocalCacheHandler extends LocalCacheHandler<LongIdKey, InspectionJobLocalCache> {
}
