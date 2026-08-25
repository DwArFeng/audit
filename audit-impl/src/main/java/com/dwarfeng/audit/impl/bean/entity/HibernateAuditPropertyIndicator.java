package com.dwarfeng.audit.impl.bean.entity;

import com.dwarfeng.audit.impl.bean.key.HibernateAuditPropertyIndicatorKey;
import com.dwarfeng.audit.sdk.util.Constraints;
import com.dwarfeng.datamark.sdk.jpa.DatamarkEntityListener;
import com.dwarfeng.datamark.sdk.jpa.DatamarkField;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;

@Entity
@IdClass(HibernateAuditPropertyIndicatorKey.class)
@Table(name = "tbl_audit_property_indicator")
@EntityListeners(DatamarkEntityListener.class)
public class HibernateAuditPropertyIndicator implements Bean {

    private static final long serialVersionUID = 5286679101242944904L;

    // region 主键

    @Id
    @Column(name = "audit_category_id", nullable = false)
    private String auditCategoryStringId;

    @Id
    @Column(name = "property_id", length = Constraints.LENGTH_PROPERTY_ID, nullable = false)
    private String propertyStringId;

    // endregion

    // region 主属性字段

    @Column(name = "label", length = Constraints.LENGTH_LABEL)
    private String label;

    @Column(name = "property_type", nullable = false)
    private int propertyType;

    @Column(name = "default_string_value", length = Constraints.LENGTH_REMARK)
    private String defaultStringValue;

    @Column(name = "default_long_value")
    private Long defaultLongValue;

    @Column(name = "default_double_value")
    private Double defaultDoubleValue;

    @Column(name = "default_boolean_value")
    private Boolean defaultBooleanValue;

    @Column(name = "default_date_value")
    @Temporal(TemporalType.TIMESTAMP)
    private Date defaultDateValue;

    @Column(name = "column_order", nullable = false)
    private int order;

    // endregion

    // region 多对一

    @ManyToOne(targetEntity = HibernateAuditCategory.class)
    @JoinColumns({ //
            @JoinColumn(name = "audit_category_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateAuditCategory auditCategory;

    // endregion

    // region 审计

    @DatamarkField(handlerName = "auditPropertyIndicatorDatamarkHandler")
    @Column(name = "created_datamark", length = com.dwarfeng.datamark.sdk.util.Constraints.LENGTH_DATAMARK_VALUE, updatable = false)
    private String createdDatamark;

    @DatamarkField(handlerName = "auditPropertyIndicatorDatamarkHandler")
    @Column(name = "modified_datamark", length = com.dwarfeng.datamark.sdk.util.Constraints.LENGTH_DATAMARK_VALUE)
    private String modifiedDatamark;

    // endregion

    public HibernateAuditPropertyIndicator() {
    }

    // region 映射用属性区

    public HibernateAuditPropertyIndicatorKey getKey() {
        if (java.util.Objects.isNull(auditCategoryStringId) || java.util.Objects.isNull(propertyStringId)) {
            return null;
        }
        return new HibernateAuditPropertyIndicatorKey(auditCategoryStringId, propertyStringId);
    }

    public void setKey(HibernateAuditPropertyIndicatorKey key) {
        if (java.util.Objects.isNull(key)) {
            this.auditCategoryStringId = null;
            this.propertyStringId = null;
        } else {
            this.auditCategoryStringId = key.getAuditCategoryStringId();
            this.propertyStringId = key.getPropertyStringId();
        }
    }

    // endregion

    // region 常规属性区

    public String getAuditCategoryStringId() {
        return auditCategoryStringId;
    }

    public void setAuditCategoryStringId(String auditCategoryStringId) {
        this.auditCategoryStringId = auditCategoryStringId;
    }

    public String getPropertyStringId() {
        return propertyStringId;
    }

    public void setPropertyStringId(String propertyStringId) {
        this.propertyStringId = propertyStringId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(int propertyType) {
        this.propertyType = propertyType;
    }

    public String getDefaultStringValue() {
        return defaultStringValue;
    }

    public void setDefaultStringValue(String defaultStringValue) {
        this.defaultStringValue = defaultStringValue;
    }

    public Long getDefaultLongValue() {
        return defaultLongValue;
    }

    public void setDefaultLongValue(Long defaultLongValue) {
        this.defaultLongValue = defaultLongValue;
    }

    public Double getDefaultDoubleValue() {
        return defaultDoubleValue;
    }

    public void setDefaultDoubleValue(Double defaultDoubleValue) {
        this.defaultDoubleValue = defaultDoubleValue;
    }

    public Boolean getDefaultBooleanValue() {
        return defaultBooleanValue;
    }

    public void setDefaultBooleanValue(Boolean defaultBooleanValue) {
        this.defaultBooleanValue = defaultBooleanValue;
    }

    public Date getDefaultDateValue() {
        return defaultDateValue;
    }

    public void setDefaultDateValue(Date defaultDateValue) {
        this.defaultDateValue = defaultDateValue;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public HibernateAuditCategory getAuditCategory() {
        return auditCategory;
    }

    public void setAuditCategory(HibernateAuditCategory auditCategory) {
        this.auditCategory = auditCategory;
    }

    public String getCreatedDatamark() {
        return createdDatamark;
    }

    public void setCreatedDatamark(String createdDatamark) {
        this.createdDatamark = createdDatamark;
    }

    public String getModifiedDatamark() {
        return modifiedDatamark;
    }

    public void setModifiedDatamark(String modifiedDatamark) {
        this.modifiedDatamark = modifiedDatamark;
    }

    // endregion

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + "auditCategoryStringId = " + auditCategoryStringId + ", " + "propertyStringId = " + propertyStringId + ", " + "label = " + label + ", " + "propertyType = " + propertyType + ", " + "defaultStringValue = " + defaultStringValue + ", " + "defaultLongValue = " + defaultLongValue + ", " + "defaultDoubleValue = " + defaultDoubleValue + ", " + "defaultBooleanValue = " + defaultBooleanValue + ", " + "defaultDateValue = " + defaultDateValue + ", " + "order = " + order + ", " + "auditCategory = " + auditCategory + ", " + "createdDatamark = " + createdDatamark + ", " + "modifiedDatamark = " + modifiedDatamark + ")";
    }
}
