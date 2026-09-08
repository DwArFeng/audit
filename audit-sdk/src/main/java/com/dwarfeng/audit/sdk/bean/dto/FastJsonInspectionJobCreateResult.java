package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.dto.InspectionJobCreateResult;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * FastJson 自动审计作业创建结果。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class FastJsonInspectionJobCreateResult implements Dto {

    private static final long serialVersionUID = -7795000223476640373L;

    public static FastJsonInspectionJobCreateResult of(InspectionJobCreateResult result) {
        if (Objects.isNull(result)) {
            return null;
        }
        return new FastJsonInspectionJobCreateResult(FastJsonLongIdKey.of(result.getInspectionTaskKey()));
    }

    @JSONField(name = "inspection_task_key", ordinal = 1)
    private FastJsonLongIdKey inspectionTaskKey;

    public FastJsonInspectionJobCreateResult() {
    }

    public FastJsonInspectionJobCreateResult(FastJsonLongIdKey inspectionTaskKey) {
        this.inspectionTaskKey = inspectionTaskKey;
    }

    public FastJsonLongIdKey getInspectionTaskKey() {
        return inspectionTaskKey;
    }

    public void setInspectionTaskKey(FastJsonLongIdKey inspectionTaskKey) {
        this.inspectionTaskKey = inspectionTaskKey;
    }

    @Override
    public String toString() {
        return "FastJsonInspectionJobCreateResult{" +
                "inspectionTaskKey=" + inspectionTaskKey +
                '}';
    }
}
