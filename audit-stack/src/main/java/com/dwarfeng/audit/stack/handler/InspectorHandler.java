package com.dwarfeng.audit.stack.handler;

import com.dwarfeng.audit.stack.exception.InspectorException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 审计器处理器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectorHandler extends Handler {

    /**
     * 根据指定的审计器信息构造审计器。
     *
     * @param type  审计器类型。
     * @param param 审计器参数。
     * @return 构造的审计器。
     * @throws InspectorException 审计器异常。
     */
    Inspector make(String type, String param) throws InspectorException;
}
