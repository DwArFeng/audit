package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.handler.InspectionDriveHandler;
import com.dwarfeng.audit.stack.handler.InspectionDriveLocalCacheHandler;
import com.dwarfeng.audit.stack.service.InspectionDriveQosService;
import com.dwarfeng.audit.stack.struct.InspectionDriveLocalCache;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

/**
 * 自动审计驱动 QoS 服务实现。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Service
public class InspectionDriveQosServiceImpl implements InspectionDriveQosService {

    private final InspectionDriveHandler inspectionDriveHandler;
    private final InspectionDriveLocalCacheHandler inspectionDriveLocalCacheHandler;
    private final ServiceExceptionMapper sem;

    public InspectionDriveQosServiceImpl(
            InspectionDriveHandler inspectionDriveHandler,
            InspectionDriveLocalCacheHandler inspectionDriveLocalCacheHandler,
            ServiceExceptionMapper sem
    ) {
        this.inspectionDriveHandler = inspectionDriveHandler;
        this.inspectionDriveLocalCacheHandler = inspectionDriveLocalCacheHandler;
        this.sem = sem;
    }

    @Override
    public boolean isStarted() throws ServiceException {
        try {
            return inspectionDriveHandler.isStarted();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("判断自动审计驱动服务是否启动时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public InspectionDriveLocalCache getInspectionDriveLocalCache(LongIdKey inspectionKey)
            throws ServiceException {
        try {
            return inspectionDriveLocalCacheHandler.get(inspectionKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("获取指定自动审计的驱动本地缓存时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void clearLocalCache() throws ServiceException {
        try {
            inspectionDriveLocalCacheHandler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("清除自动审计驱动本地缓存时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
