package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.audit.stack.bean.entity.AuditEntryProperty;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.List;

/**
 * 审计记录数据。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class AuditRecordData implements Dto {

    private static final long serialVersionUID = 1921744547462015332L;

    private AuditEntry auditEntry;
    private List<AuditEntryProperty> auditEntryProperties;

    public AuditRecordData() {
    }

    public AuditRecordData(AuditEntry auditEntry, List<AuditEntryProperty> auditEntryProperties) {
        this.auditEntry = auditEntry;
        this.auditEntryProperties = auditEntryProperties;
    }

    public AuditEntry getAuditEntry() {
        return auditEntry;
    }

    public void setAuditEntry(AuditEntry auditEntry) {
        this.auditEntry = auditEntry;
    }

    public List<AuditEntryProperty> getAuditEntryProperties() {
        return auditEntryProperties;
    }

    public void setAuditEntryProperties(List<AuditEntryProperty> auditEntryProperties) {
        this.auditEntryProperties = auditEntryProperties;
    }

    @Override
    public String toString() {
        return "AuditRecordData{" +
                "auditEntry=" + auditEntry +
                ", auditEntryProperties=" + auditEntryProperties +
                '}';
    }
}
