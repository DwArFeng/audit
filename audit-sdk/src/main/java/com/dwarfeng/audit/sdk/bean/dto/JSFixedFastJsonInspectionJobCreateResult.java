package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.dto.InspectionJobCreateResult;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 自动审计作业创建结果。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class JSFixedFastJsonInspectionJobCreateResult implements Dto {

    private static final long serialVersionUID = -4601223209771083668L;

    public static JSFixedFastJsonInspectionJobCreateResult of(InspectionJobCreateResult result) {
        if (Objects.isNull(result)) {
            return null;
        }
        return new JSFixedFastJsonInspectionJobCreateResult(
                JSFixedFastJsonLongIdKey.of(result.getInspectionTaskKey())
        );
    }

    @JSONField(name = "inspection_task_key", ordinal = 1)
    private JSFixedFastJsonLongIdKey inspectionTaskKey;

    public JSFixedFastJsonInspectionJobCreateResult() {
    }

    public JSFixedFastJsonInspectionJobCreateResult(JSFixedFastJsonLongIdKey inspectionTaskKey) {
        this.inspectionTaskKey = inspectionTaskKey;
    }

    public JSFixedFastJsonLongIdKey getInspectionTaskKey() {
        return inspectionTaskKey;
    }

    public void setInspectionTaskKey(JSFixedFastJsonLongIdKey inspectionTaskKey) {
        this.inspectionTaskKey = inspectionTaskKey;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonInspectionJobCreateResult{" +
                "inspectionTaskKey=" + inspectionTaskKey +
                '}';
    }
}
