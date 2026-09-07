package com.dwarfeng.audit.stack.service;

import com.dwarfeng.audit.stack.handler.InspectionDispatcher;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 自动审计调度 QoS 服务。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionDispatchQosService extends Service {

    /**
     * 判断调度服务是否启动。
     *
     * @return 调度服务是否启动。
     * @throws ServiceException 服务异常。
     */
    boolean isStarted() throws ServiceException;

    /**
     * 启动调度服务。
     *
     * @throws ServiceException 服务异常。
     */
    void start() throws ServiceException;

    /**
     * 停止调度服务。
     *
     * @throws ServiceException 服务异常。
     */
    void stop() throws ServiceException;

    /**
     * 获取当前正在使用的调度器。
     *
     * @return 当前正在使用的调度器。
     * @throws ServiceException 服务异常。
     */
    InspectionDispatcher currentDispatcher() throws ServiceException;

    /**
     * 获取所有调度器。
     *
     * @return 所有调度器组成的列表。
     * @throws ServiceException 服务异常。
     */
    List<InspectionDispatcher> allDispatchers() throws ServiceException;
}
