package com.dwarfeng.audit.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.StartableHandler;

/**
 * 重置处理器。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public interface ResetHandler extends StartableHandler {

    /**
     * 重置审计记录功能。
     *
     * @throws HandlerException 处理器异常。
     */
    void resetAuditRecord() throws HandlerException;

    /**
     * 重置自动审计主管功能。
     *
     * @throws HandlerException 处理器异常。
     * @since 1.1.0
     */
    void resetInspectionSupervise() throws HandlerException;

    /**
     * 重置自动审计作业功能。
     *
     * @throws HandlerException 处理器异常。
     * @since 1.1.0
     */
    void resetInspectionJob() throws HandlerException;
}
