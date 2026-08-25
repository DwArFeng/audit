package com.dwarfeng.audit.impl.bean.entity;

import com.dwarfeng.audit.sdk.util.Constraints;
import com.dwarfeng.datamark.sdk.jpa.DatamarkEntityListener;
import com.dwarfeng.datamark.sdk.jpa.DatamarkField;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@IdClass(HibernateStringIdKey.class)
@Table(name = "tbl_audit_category")
@EntityListeners(DatamarkEntityListener.class)
public class HibernateAuditCategory implements Bean {

    private static final long serialVersionUID = -6270382629424601301L;

    // region 主键

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String stringId;

    // endregion

    // region 主属性字段

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "name", length = Constraints.LENGTH_NAME)
    private String name;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // endregion

    // region 一对多

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateAuditPropertyIndicator.class, mappedBy = "auditCategory")
    private Set<HibernateAuditPropertyIndicator> auditPropertyIndicatorSet = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateAuditEntry.class, mappedBy = "auditCategory")
    private Set<HibernateAuditEntry> auditEntrySet = new HashSet<>();

    // endregion

    // region 审计

    @DatamarkField(handlerName = "auditCategoryDatamarkHandler")
    @Column(
            name = "created_datamark",
            length = com.dwarfeng.datamark.sdk.util.Constraints.LENGTH_DATAMARK_VALUE,
            updatable = false
    )
    private String createdDatamark;

    @DatamarkField(handlerName = "auditCategoryDatamarkHandler")
    @Column(
            name = "modified_datamark",
            length = com.dwarfeng.datamark.sdk.util.Constraints.LENGTH_DATAMARK_VALUE
    )
    private String modifiedDatamark;

    // endregion

    public HibernateAuditCategory() {
    }

    // region 映射用属性区

    public HibernateStringIdKey getKey() {
        return Optional.ofNullable(stringId).map(HibernateStringIdKey::new).orElse(null);
    }

    public void setKey(HibernateStringIdKey key) {
        this.stringId = Optional.ofNullable(key).map(HibernateStringIdKey::getStringId).orElse(null);
    }

    // endregion

    // region 常规属性区

    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Set<HibernateAuditPropertyIndicator> getAuditPropertyIndicatorSet() {
        return auditPropertyIndicatorSet;
    }

    public void setAuditPropertyIndicatorSet(Set<HibernateAuditPropertyIndicator> auditPropertyIndicatorSet) {
        this.auditPropertyIndicatorSet = auditPropertyIndicatorSet;
    }

    public Set<HibernateAuditEntry> getAuditEntrySet() {
        return auditEntrySet;
    }

    public void setAuditEntrySet(Set<HibernateAuditEntry> auditEntrySet) {
        this.auditEntrySet = auditEntrySet;
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
        return getClass().getSimpleName() + "(" +
                "stringId = " + stringId + ", " +
                "enabled = " + enabled + ", " +
                "name = " + name + ", " +
                "remark = " + remark + ", " +
                "createdDatamark = " + createdDatamark + ", " +
                "modifiedDatamark = " + modifiedDatamark + ")";
    }
}
