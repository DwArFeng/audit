package com.dwarfeng.audit.stack.struct;

import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.bean.entity.InspectionDriverInfo;
import com.dwarfeng.audit.stack.handler.InspectionDriver;

import java.util.Map;

/**
 * 驱动本地缓存。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public final class InspectionDriveLocalCache {

    private final Inspection inspection;
    private final Map<InspectionDriverInfo, InspectionDriver> driverMap;

    public InspectionDriveLocalCache(Inspection inspection, Map<InspectionDriverInfo, InspectionDriver> driverMap) {
        this.inspection = inspection;
        this.driverMap = driverMap;
    }

    public Inspection getInspection() {
        return inspection;
    }

    public Map<InspectionDriverInfo, InspectionDriver> getDriverMap() {
        return driverMap;
    }

    @Override
    public String toString() {
        return "InspectionDriveLocalCache{" +
                "inspection=" + inspection +
                ", driverMap=" + driverMap +
                '}';
    }
}
