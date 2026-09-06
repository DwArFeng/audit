package com.dwarfeng.audit.stack.service;

import com.dwarfeng.audit.stack.handler.InspectionReceiver;
import com.dwarfeng.audit.stack.struct.InspectionReceiverConsumerStatus;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 自动审计接收器 QoS 服务。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionReceiverQosService extends Service {

    /**
     * 判断接收服务是否启动。
     *
     * @return 接收服务是否启动。
     * @throws ServiceException 服务异常。
     */
    boolean isStarted() throws ServiceException;

    /**
     * 启动接收服务。
     *
     * @throws ServiceException 服务异常。
     */
    void start() throws ServiceException;

    /**
     * 停止接收服务。
     *
     * @throws ServiceException 服务异常。
     */
    void stop() throws ServiceException;

    /**
     * 获取当前正在使用的接收器。
     *
     * @return 当前正在使用的接收器。
     * @throws ServiceException 服务异常。
     */
    InspectionReceiver currentReceiver() throws ServiceException;

    /**
     * 获取所有接收器。
     *
     * @return 所有接收器组成的列表。
     * @throws ServiceException 服务异常。
     */
    List<InspectionReceiver> allReceivers() throws ServiceException;

    /**
     * 获取消费者状态。
     *
     * @return 消费者状态。
     * @throws ServiceException 服务异常。
     */
    InspectionReceiverConsumerStatus getConsumerStatus() throws ServiceException;

    /**
     * 设置消费者参数。
     *
     * @param bufferSize 缓冲器容量，为 null 时不变。
     * @param thread     消费线程数量，为 null 时不变。
     * @throws ServiceException 服务异常。
     */
    void setConsumerParameters(Integer bufferSize, Integer thread) throws ServiceException;
}
