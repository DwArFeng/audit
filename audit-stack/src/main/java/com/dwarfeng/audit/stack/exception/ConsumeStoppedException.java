package com.dwarfeng.audit.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 消费处理器已停止异常。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class ConsumeStoppedException extends HandlerException {

    private static final long serialVersionUID = -1759659658706081718L;

    @Override
    public String getMessage() {
        return "消费处理器尚未启动或已经停止";
    }
}
