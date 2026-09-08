package com.dwarfeng.audit.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 驱动执行器处理器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionDriverHandler extends Handler {

    /**
     * 查找指定类型的驱动器。
     *
     * @param type 驱动器类型。
     * @return 驱动器。
     * @throws HandlerException 处理器异常。
     */
    InspectionDriver find(String type) throws HandlerException;
}
