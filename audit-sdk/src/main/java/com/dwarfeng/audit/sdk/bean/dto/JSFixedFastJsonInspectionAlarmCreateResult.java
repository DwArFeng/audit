package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.dto.InspectionAlarmCreateResult;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * JSFixed FastJson 自动审计报警创建结果。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class JSFixedFastJsonInspectionAlarmCreateResult implements Dto {

    private static final long serialVersionUID = -6436206054424892900L;

    public static JSFixedFastJsonInspectionAlarmCreateResult of(
            InspectionAlarmCreateResult inspectionAlarmCreateResult
    ) {
        if (Objects.isNull(inspectionAlarmCreateResult)) {
            return null;
        } else {
            return new JSFixedFastJsonInspectionAlarmCreateResult(
                    JSFixedFastJsonLongIdKey.of(inspectionAlarmCreateResult.getInspectionAlarmKey())
            );
        }
    }

    @JSONField(name = "inspection_alarm_key", ordinal = 1)
    private JSFixedFastJsonLongIdKey inspectionAlarmKey;

    public JSFixedFastJsonInspectionAlarmCreateResult() {
    }

    public JSFixedFastJsonInspectionAlarmCreateResult(JSFixedFastJsonLongIdKey inspectionAlarmKey) {
        this.inspectionAlarmKey = inspectionAlarmKey;
    }

    public JSFixedFastJsonLongIdKey getInspectionAlarmKey() {
        return inspectionAlarmKey;
    }

    public void setInspectionAlarmKey(JSFixedFastJsonLongIdKey inspectionAlarmKey) {
        this.inspectionAlarmKey = inspectionAlarmKey;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonInspectionAlarmCreateResult{" +
                "inspectionAlarmKey=" + inspectionAlarmKey +
                '}';
    }
}
