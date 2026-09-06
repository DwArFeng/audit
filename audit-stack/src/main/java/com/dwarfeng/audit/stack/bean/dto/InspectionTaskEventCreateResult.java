package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 自动审计任务事件创建结果。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionTaskEventCreateResult implements Dto {

    private static final long serialVersionUID = 1142849344607597120L;

    private LongIdKey inspectionTaskEventKey;

    public InspectionTaskEventCreateResult() {
    }

    public InspectionTaskEventCreateResult(LongIdKey inspectionTaskEventKey) {
        this.inspectionTaskEventKey = inspectionTaskEventKey;
    }

    public LongIdKey getInspectionTaskEventKey() {
        return inspectionTaskEventKey;
    }

    public void setInspectionTaskEventKey(LongIdKey inspectionTaskEventKey) {
        this.inspectionTaskEventKey = inspectionTaskEventKey;
    }

    @Override
    public String toString() {
        return "InspectionTaskEventCreateResult{" +
                "inspectionTaskEventKey=" + inspectionTaskEventKey +
                '}';
    }
}
