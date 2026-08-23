package com.dwarfeng.audit.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 推送处理器。
 *
 * <p>
 * 该处理器负责将审计服务内部发生的审核记录重置事件转换为外部可消费的推送事件。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public interface PushHandler extends Handler {

    /**
     * 审核记录功能重置时执行的推送操作。
     *
     * @throws HandlerException 处理器异常。
     */
    void auditRecordReset() throws HandlerException;
}
