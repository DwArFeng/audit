package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 审计器变量删除信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectorVariableRemoveInfo implements Dto {

    private static final long serialVersionUID = 3759740058534174824L;

    private LongIdKey inspectorInfoKey;
    private String inspectorVariableId;

    public InspectorVariableRemoveInfo() {
    }

    public InspectorVariableRemoveInfo(LongIdKey inspectorInfoKey, String inspectorVariableId) {
        this.inspectorInfoKey = inspectorInfoKey;
        this.inspectorVariableId = inspectorVariableId;
    }

    public LongIdKey getInspectorInfoKey() {
        return inspectorInfoKey;
    }

    public void setInspectorInfoKey(LongIdKey inspectorInfoKey) {
        this.inspectorInfoKey = inspectorInfoKey;
    }

    public String getInspectorVariableId() {
        return inspectorVariableId;
    }

    public void setInspectorVariableId(String inspectorVariableId) {
        this.inspectorVariableId = inspectorVariableId;
    }

    @Override
    public String toString() {
        return "InspectorVariableRemoveInfo{" +
                "inspectorInfoKey=" + inspectorInfoKey +
                ", inspectorVariableId='" + inspectorVariableId + '\'' +
                '}';
    }
}
