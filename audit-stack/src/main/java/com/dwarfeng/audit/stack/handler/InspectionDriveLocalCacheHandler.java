package com.dwarfeng.audit.stack.handler;

import com.dwarfeng.audit.stack.struct.InspectionDriveLocalCache;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.handler.LocalCacheHandler;

/**
 * 驱动用本地缓存处理器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionDriveLocalCacheHandler extends LocalCacheHandler<LongIdKey, InspectionDriveLocalCache> {
}
