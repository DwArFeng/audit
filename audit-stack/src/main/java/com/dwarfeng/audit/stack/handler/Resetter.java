package com.dwarfeng.audit.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 重置器。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public interface Resetter {

    /**
     * 初始化重置器。
     *
     * <p>
     * 该方法会在重置器初始化后调用，重置器应将 context 存放在字段中，并在触发时调用上下文的重置方法。
     *
     * @param context 重置器的上下文。
     */
    void init(Context context);

    /**
     * 启动重置器。
     *
     * @throws HandlerException 处理器异常。
     */
    void start() throws HandlerException;

    /**
     * 停止重置器。
     *
     * @throws HandlerException 处理器异常。
     */
    void stop() throws HandlerException;

    /**
     * 重置器上下文。
     *
     * @author DwArFeng
     * @since 1.0.0-beta
     */
    interface Context {

        /**
         * 重置审计记录功能。
         *
         * @throws Exception 执行重置时抛出的任何异常。
         */
        void resetAuditRecord() throws Exception;

        /**
         * 重置自动审计主管功能。
         *
         * @throws Exception 执行重置时抛出的任何异常。
         * @since 1.1.0
         */
        void resetInspectionSupervise() throws Exception;

        /**
         * 重置自动审计作业功能。
         *
         * @throws Exception 执行重置时抛出的任何异常。
         * @since 1.1.0
         */
        void resetInspectionJob() throws Exception;
    }
}
