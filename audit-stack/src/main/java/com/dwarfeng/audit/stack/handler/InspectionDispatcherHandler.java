package com.dwarfeng.audit.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.List;

/**
 * 调度器处理器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionDispatcherHandler extends Handler {

    /**
     * 返回当前使用的调度器。
     *
     * @return 当前调度器。
     * @throws HandlerException 处理器异常。
     */
    InspectionDispatcher current() throws HandlerException;

    /**
     * 返回全部调度器。
     *
     * @return 调度器列表。
     * @throws HandlerException 处理器异常。
     */
    List<InspectionDispatcher> all() throws HandlerException;
}
