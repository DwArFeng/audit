package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 自动审计报警创建结果。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionAlarmCreateResult implements Dto {

    private static final long serialVersionUID = 3047988555604886353L;

    private LongIdKey inspectionAlarmKey;

    public InspectionAlarmCreateResult() {
    }

    public InspectionAlarmCreateResult(LongIdKey inspectionAlarmKey) {
        this.inspectionAlarmKey = inspectionAlarmKey;
    }

    public LongIdKey getInspectionAlarmKey() {
        return inspectionAlarmKey;
    }

    public void setInspectionAlarmKey(LongIdKey inspectionAlarmKey) {
        this.inspectionAlarmKey = inspectionAlarmKey;
    }

    @Override
    public String toString() {
        return "InspectionAlarmCreateResult{" +
                "inspectionAlarmKey=" + inspectionAlarmKey +
                '}';
    }
}
