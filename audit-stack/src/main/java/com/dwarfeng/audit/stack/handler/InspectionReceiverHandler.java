package com.dwarfeng.audit.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.List;

/**
 * 接收器处理器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionReceiverHandler extends Handler {

    /**
     * 返回当前使用的接收器。
     *
     * @return 当前接收器。
     * @throws HandlerException 处理器异常。
     */
    InspectionReceiver current() throws HandlerException;

    /**
     * 返回全部接收器。
     *
     * @return 接收器列表。
     * @throws HandlerException 处理器异常。
     */
    List<InspectionReceiver> all() throws HandlerException;
}
