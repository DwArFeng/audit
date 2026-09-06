package com.dwarfeng.audit.impl.bean.entity;

import com.dwarfeng.audit.impl.bean.key.HibernateInspectorVariableKey;
import com.dwarfeng.audit.sdk.util.Constraints;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;

@Entity
@IdClass(HibernateInspectorVariableKey.class)
@Table(name = "tbl_inspector_variable")
public class HibernateInspectorVariable implements Bean {

    private static final long serialVersionUID = -6669820590098835174L;

    // region 主键

    @Id
    @Column(name = "inspector_info_id", nullable = false)
    private Long inspectorInfoLongId;

    @Id
    @Column(name = "variable_id", length = Constraints.LENGTH_STRING_ID, nullable = false)
    private String variableStringId;

    // endregion

    // region 主属性字段

    @Column(name = "value_type", nullable = false)
    private int valueType;

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

    @ManyToOne(targetEntity = HibernateInspectorInfo.class)
    @JoinColumns({ //
            @JoinColumn(name = "inspector_info_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateInspectorInfo inspectorInfo;

    // endregion

    public HibernateInspectorVariable() {
    }

    // region 映射用属性区

    public HibernateInspectorVariableKey getKey() {
        if (java.util.Objects.isNull(inspectorInfoLongId) || java.util.Objects.isNull(variableStringId)) {
            return null;
        }
        return new HibernateInspectorVariableKey(inspectorInfoLongId, variableStringId);
    }

    public void setKey(HibernateInspectorVariableKey key) {
        if (java.util.Objects.isNull(key)) {
            this.inspectorInfoLongId = null;
            this.variableStringId = null;
        } else {
            this.inspectorInfoLongId = key.getInspectorInfoLongId();
            this.variableStringId = key.getVariableStringId();
        }
    }

    // endregion

    // region 常规属性区

    public Long getInspectorInfoLongId() {
        return inspectorInfoLongId;
    }

    public void setInspectorInfoLongId(Long inspectorInfoLongId) {
        this.inspectorInfoLongId = inspectorInfoLongId;
    }

    public String getVariableStringId() {
        return variableStringId;
    }

    public void setVariableStringId(String variableStringId) {
        this.variableStringId = variableStringId;
    }

    public int getValueType() {
        return valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
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

    public HibernateInspectorInfo getInspectorInfo() {
        return inspectorInfo;
    }

    public void setInspectorInfo(HibernateInspectorInfo inspectorInfo) {
        this.inspectorInfo = inspectorInfo;
    }

    // endregion

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "inspectorInfoLongId = " + inspectorInfoLongId + ", " +
                "variableStringId = " + variableStringId + ", " +
                "valueType = " + valueType + ", " +
                "stringValue = " + stringValue + ", " +
                "longValue = " + longValue + ", " +
                "doubleValue = " + doubleValue + ", " +
                "booleanValue = " + booleanValue + ", " +
                "dateValue = " + dateValue + ", " +
                "inspectorInfo = " + inspectorInfo + ")";
    }
}
