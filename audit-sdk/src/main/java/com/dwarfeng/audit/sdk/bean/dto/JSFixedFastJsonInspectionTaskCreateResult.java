package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.dto.InspectionTaskCreateResult;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 自动审计任务创建结果。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class JSFixedFastJsonInspectionTaskCreateResult implements Dto {

    private static final long serialVersionUID = 6267420816909562602L;

    public static JSFixedFastJsonInspectionTaskCreateResult of(InspectionTaskCreateResult result) {
        if (Objects.isNull(result)) {
            return null;
        } else {
            return new JSFixedFastJsonInspectionTaskCreateResult(
                    JSFixedFastJsonLongIdKey.of(result.getInspectionTaskKey())
            );
        }
    }

    @JSONField(name = "inspection_task_key", ordinal = 1)
    private JSFixedFastJsonLongIdKey inspectionTaskKey;

    public JSFixedFastJsonInspectionTaskCreateResult() {
    }

    public JSFixedFastJsonInspectionTaskCreateResult(JSFixedFastJsonLongIdKey inspectionTaskKey) {
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
        return "JSFixedFastJsonInspectionTaskCreateResult{" +
                "inspectionTaskKey=" + inspectionTaskKey +
                '}';
    }
}
