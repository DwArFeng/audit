package com.dwarfeng.audit.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 支持处理器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface SupportHandler extends Handler {

    /**
     * 重置自动审计驱动器支持。
     *
     * @throws HandlerException 处理器异常。
     * @since 1.1.0
     */
    void resetInspectionDriver() throws HandlerException;

    /**
     * 重置审计器。
     *
     * @throws HandlerException 处理器异常。
     * @since 1.1.0
     */
    void resetInspector() throws HandlerException;
}
