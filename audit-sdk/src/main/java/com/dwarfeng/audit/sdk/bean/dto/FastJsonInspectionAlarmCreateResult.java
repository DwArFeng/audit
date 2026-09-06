package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.dto.InspectionAlarmCreateResult;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * FastJson 自动审计报警创建结果。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class FastJsonInspectionAlarmCreateResult implements Dto {

    private static final long serialVersionUID = -7477350213428559764L;

    public static FastJsonInspectionAlarmCreateResult of(InspectionAlarmCreateResult inspectionAlarmCreateResult) {
        if (Objects.isNull(inspectionAlarmCreateResult)) {
            return null;
        } else {
            return new FastJsonInspectionAlarmCreateResult(
                    FastJsonLongIdKey.of(inspectionAlarmCreateResult.getInspectionAlarmKey())
            );
        }
    }

    @JSONField(name = "inspection_alarm_key", ordinal = 1)
    private FastJsonLongIdKey inspectionAlarmKey;

    public FastJsonInspectionAlarmCreateResult() {
    }

    public FastJsonInspectionAlarmCreateResult(FastJsonLongIdKey inspectionAlarmKey) {
        this.inspectionAlarmKey = inspectionAlarmKey;
    }

    public FastJsonLongIdKey getInspectionAlarmKey() {
        return inspectionAlarmKey;
    }

    public void setInspectionAlarmKey(FastJsonLongIdKey inspectionAlarmKey) {
        this.inspectionAlarmKey = inspectionAlarmKey;
    }

    @Override
    public String toString() {
        return "FastJsonInspectionAlarmCreateResult{" +
                "inspectionAlarmKey=" + inspectionAlarmKey +
                '}';
    }
}
