package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.dto.InspectionTaskCreateResult;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * FastJson 自动审计任务创建结果。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class FastJsonInspectionTaskCreateResult implements Dto {

    private static final long serialVersionUID = -3838734424297488247L;

    public static FastJsonInspectionTaskCreateResult of(InspectionTaskCreateResult result) {
        if (Objects.isNull(result)) {
            return null;
        } else {
            return new FastJsonInspectionTaskCreateResult(FastJsonLongIdKey.of(result.getInspectionTaskKey()));
        }
    }

    @JSONField(name = "inspection_task_key", ordinal = 1)
    private FastJsonLongIdKey inspectionTaskKey;

    public FastJsonInspectionTaskCreateResult() {
    }

    public FastJsonInspectionTaskCreateResult(FastJsonLongIdKey inspectionTaskKey) {
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
        return "FastJsonInspectionTaskCreateResult{" +
                "inspectionTaskKey=" + inspectionTaskKey +
                '}';
    }
}
