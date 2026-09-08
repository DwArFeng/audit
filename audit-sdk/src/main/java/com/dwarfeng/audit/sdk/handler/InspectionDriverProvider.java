package com.dwarfeng.audit.sdk.handler;

import com.dwarfeng.audit.stack.handler.InspectionDriver;

/**
 * 驱动器提供器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionDriverProvider {

    /**
     * 返回提供器是否支持指定类型。
     *
     * @param type 指定类型。
     * @return 是否支持。
     */
    boolean supportType(String type);

    /**
     * 提供驱动器实例。
     *
     * @return 驱动器实例。
     */
    InspectionDriver provide();
}
