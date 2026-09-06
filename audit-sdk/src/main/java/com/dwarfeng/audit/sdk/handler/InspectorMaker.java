package com.dwarfeng.audit.sdk.handler;

import com.dwarfeng.audit.stack.exception.InspectorException;
import com.dwarfeng.audit.stack.handler.Inspector;

/**
 * 审计器构造器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectorMaker {

    /**
     * 返回构造器是否支持指定的审计器类型。
     *
     * @param type 审计器类型。
     * @return 构造器是否支持指定的审计器类型。
     */
    boolean supportType(String type);

    /**
     * 根据指定的审计器信息构造审计器。
     *
     * <p>
     * 可以保证传入的审计器类型是该构造器支持的类型。
     *
     * @param type  审计器类型。
     * @param param 审计器参数。
     * @return 构造的审计器。
     * @throws InspectorException 审计器异常。
     */
    Inspector makeInspector(String type, String param) throws InspectorException;
}
