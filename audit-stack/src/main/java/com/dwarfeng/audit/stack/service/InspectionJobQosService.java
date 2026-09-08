package com.dwarfeng.audit.stack.service;

import com.dwarfeng.audit.stack.struct.InspectionJobLocalCache;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 自动审计作业 QoS 服务。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionJobQosService extends Service {

    /**
     * 获取指定自动审计的本地缓存。
     *
     * @param inspectionKey 自动审计主键。
     * @return 自动审计作业本地缓存。
     * @throws ServiceException 服务异常。
     */
    InspectionJobLocalCache getInspectionJobLocalCache(LongIdKey inspectionKey) throws ServiceException;

    /**
     * 清除自动审计作业本地缓存。
     *
     * @throws ServiceException 服务异常。
     */
    void clearLocalCache() throws ServiceException;

    /**
     * 创建并执行指定自动审计作业。
     *
     * @param inspectionKey 自动审计主键。
     * @throws ServiceException 服务异常。
     */
    void execute(LongIdKey inspectionKey) throws ServiceException;
}
