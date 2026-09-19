package com.dwarfeng.audit.impl.bean.entity;

import com.dwarfeng.audit.impl.bean.key.HibernateAuditEntryPropertyKey;
import com.dwarfeng.audit.sdk.util.Constraints;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;

@Entity
@IdClass(HibernateAuditEntryPropertyKey.class)
@Table(name = "tbl_audit_entry_property")
public class HibernateAuditEntryProperty implements Bean {

    private static final long serialVersionUID = -5674013051564168972L;

    // region 主键

    @Id
    @Column(name = "audit_entry_id", nullable = false)
    private Long auditEntryLongId;

    @Id
    @Column(name = "property_id", length = Constraints.LENGTH_PROPERTY_ID, nullable = false)
    private String propertyStringId;

    // endregion

    // region 主属性字段

    @Column(name = "property_type", nullable = false)
    private int propertyType;

    @Column(name = "string_value", columnDefinition = "TEXT")
    private String stringValue;

    @Column(name = "long_value")
    private Long longValue;

    @Column(name = "double_value")
    private Double doubleValue;

    @Column(name = "boolean_value")
    private Boolean booleanValue;

    @Column(name = "date_value")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateValue;

    // endregion

    // region 多对一

    @ManyToOne(targetEntity = HibernateAuditEntry.class)
    @JoinColumns({ //
            @JoinColumn(name = "audit_entry_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateAuditEntry auditEntry;

    // endregion

    public HibernateAuditEntryProperty() {
    }

    // region 映射用属性区

    public HibernateAuditEntryPropertyKey getKey() {
        if (java.util.Objects.isNull(auditEntryLongId) || java.util.Objects.isNull(propertyStringId)) {
            return null;
        }
        return new HibernateAuditEntryPropertyKey(auditEntryLongId, propertyStringId);
    }

    public void setKey(HibernateAuditEntryPropertyKey key) {
        if (java.util.Objects.isNull(key)) {
            this.auditEntryLongId = null;
            this.propertyStringId = null;
        } else {
            this.auditEntryLongId = key.getAuditEntryLongId();
            this.propertyStringId = key.getPropertyStringId();
        }
    }

    // endregion

    // region 常规属性区

    public Long getAuditEntryLongId() {
        return auditEntryLongId;
    }

    public void setAuditEntryLongId(Long auditEntryLongId) {
        this.auditEntryLongId = auditEntryLongId;
    }

    public String getPropertyStringId() {
        return propertyStringId;
    }

    public void setPropertyStringId(String propertyStringId) {
        this.propertyStringId = propertyStringId;
    }

    public int getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(int propertyType) {
        this.propertyType = propertyType;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Long getLongValue() {
        return longValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    public HibernateAuditEntry getAuditEntry() {
        return auditEntry;
    }

    public void setAuditEntry(HibernateAuditEntry auditEntry) {
        this.auditEntry = auditEntry;
    }

    // endregion

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "auditEntryLongId = " + auditEntryLongId + ", " +
                "propertyStringId = " + propertyStringId + ", " +
                "propertyType = " + propertyType + ", " +
                "stringValue = " + stringValue + ", " +
                "longValue = " + longValue + ", " +
                "doubleValue = " + doubleValue + ", " +
                "booleanValue = " + booleanValue + ", " +
                "dateValue = " + dateValue + ", " +
                "auditEntry = " + auditEntry + ")";
    }
}
