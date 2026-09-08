package com.dwarfeng.audit.stack.struct;

import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.bean.entity.InspectorInfo;
import com.dwarfeng.audit.stack.handler.Inspector;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.List;
import java.util.Map;

/**
 * 自动审计作业本地缓存。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public final class InspectionJobLocalCache {

    private final Inspection inspection;
    private final List<InspectorInfo> inspectorInfos;
    private final Map<LongIdKey, Inspector> inspectorMap;

    public InspectionJobLocalCache(
            Inspection inspection,
            List<InspectorInfo> inspectorInfos,
            Map<LongIdKey, Inspector> inspectorMap
    ) {
        this.inspection = inspection;
        this.inspectorInfos = inspectorInfos;
        this.inspectorMap = inspectorMap;
    }

    public Inspection getInspection() {
        return inspection;
    }

    public List<InspectorInfo> getInspectorInfos() {
        return inspectorInfos;
    }

    public Map<LongIdKey, Inspector> getInspectorMap() {
        return inspectorMap;
    }

    @Override
    public String toString() {
        return "InspectionJobLocalCache{" +
                "inspection=" + inspection +
                ", inspectorInfos=" + inspectorInfos +
                ", inspectorMap=" + inspectorMap +
                '}';
    }
}
