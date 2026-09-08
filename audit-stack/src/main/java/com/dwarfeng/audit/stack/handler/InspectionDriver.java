package com.dwarfeng.audit.stack.handler;

import com.dwarfeng.audit.stack.bean.entity.InspectionDriverInfo;
import com.dwarfeng.audit.stack.exception.InspectionDriverException;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 自动审计驱动器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionDriver {

    /**
     * 初始化驱动器。
     *
     * @param context 驱动器上下文。
     */
    void init(Context context);

    /**
     * 注册驱动信息。
     *
     * @param driverInfo 驱动信息。
     * @throws InspectionDriverException 驱动器异常。
     */
    void register(InspectionDriverInfo driverInfo) throws InspectionDriverException;

    /**
     * 解除全部驱动信息。
     *
     * @throws InspectionDriverException 驱动器异常。
     */
    void unregisterAll() throws InspectionDriverException;

    /**
     * 驱动器上下文。
     */
    interface Context {

        /**
         * 执行指定自动审计。
         *
         * @param inspectionKey 自动审计主键。
         * @throws InspectionDriverException 驱动器异常。
         */
        void execute(LongIdKey inspectionKey) throws InspectionDriverException;
    }
}
