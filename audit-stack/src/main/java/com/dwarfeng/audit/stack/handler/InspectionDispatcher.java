package com.dwarfeng.audit.stack.handler;

import com.dwarfeng.audit.stack.exception.InspectionDispatcherException;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 自动审计调度器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionDispatcher {

    /**
     * 返回调度器是否支持指定类型。
     *
     * @param type 指定的类型。
     * @return 是否支持。
     */
    boolean supportType(String type);

    /**
     * 启动调度器。
     *
     * @throws InspectionDispatcherException 调度器异常。
     */
    void start() throws InspectionDispatcherException;

    /**
     * 停止调度器。
     *
     * @throws InspectionDispatcherException 调度器异常。
     */
    void stop() throws InspectionDispatcherException;

    /**
     * 调度指定自动审计。
     *
     * @param inspectionKey 自动审计主键。
     * @throws InspectionDispatcherException 调度器异常。
     */
    void dispatch(LongIdKey inspectionKey) throws InspectionDispatcherException;
}
