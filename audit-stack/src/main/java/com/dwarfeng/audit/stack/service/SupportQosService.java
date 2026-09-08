package com.dwarfeng.audit.stack.service;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 支持 QoS 服务。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface SupportQosService extends Service {

    /**
     * 重置自动审计驱动器支持。
     *
     * @throws ServiceException 服务异常。
     * @since 1.1.0
     */
    void resetInspectionDriver() throws ServiceException;

    /**
     * 重置审计器。
     *
     * @throws ServiceException 服务异常。
     * @since 1.1.0
     */
    void resetInspector() throws ServiceException;
}
