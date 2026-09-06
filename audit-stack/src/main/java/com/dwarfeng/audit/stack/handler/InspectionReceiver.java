package com.dwarfeng.audit.stack.handler;

import com.dwarfeng.audit.stack.exception.InspectionReceiverException;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 自动审计接收器。
 *
 * <p>
 * 接收器负责接收外部调度请求，并调用上下文执行对应的自动审计。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionReceiver {

    /**
     * 返回接收器是否支持指定类型。
     *
     * @param type 指定的类型。
     * @return 是否支持。
     */
    boolean supportType(String type);

    /**
     * 初始化接收器。
     *
     * @param context 接收器上下文。
     */
    void init(Context context);

    /**
     * 启动接收器。
     *
     * @throws InspectionReceiverException 接收器异常。
     */
    void start() throws InspectionReceiverException;

    /**
     * 停止接收器。
     *
     * @throws InspectionReceiverException 接收器异常。
     */
    void stop() throws InspectionReceiverException;

    /**
     * 接收器上下文。
     */
    interface Context {

        /**
         * 异步执行指定的自动审计。
         *
         * @param inspectionKey 自动审计主键。
         * @throws InspectionReceiverException 接收器异常。
         */
        void execute(LongIdKey inspectionKey) throws InspectionReceiverException;
    }
}
