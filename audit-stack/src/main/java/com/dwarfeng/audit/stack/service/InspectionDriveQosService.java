package com.dwarfeng.audit.stack.service;

import com.dwarfeng.audit.stack.struct.InspectionDriveLocalCache;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 自动审计驱动 QoS 服务。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionDriveQosService extends Service {

    /**
     * 自动审计驱动服务是否启动。
     *
     * @return 自动审计驱动服务是否启动。
     * @throws ServiceException 服务异常。
     */
    boolean isStarted() throws ServiceException;

    /**
     * 获取指定自动审计的驱动本地缓存。
     *
     * @param inspectionKey 指定自动审计的主键。
     * @return 指定自动审计的驱动本地缓存。
     * @throws ServiceException 服务异常。
     */
    InspectionDriveLocalCache getInspectionDriveLocalCache(LongIdKey inspectionKey) throws ServiceException;

    /**
     * 清除驱动本地缓存。
     *
     * @throws ServiceException 服务异常。
     */
    void clearLocalCache() throws ServiceException;
}
