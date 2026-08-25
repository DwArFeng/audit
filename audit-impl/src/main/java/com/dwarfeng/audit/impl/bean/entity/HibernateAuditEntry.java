package com.dwarfeng.audit.impl.bean.entity;

import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_audit_entry")
public class HibernateAuditEntry implements Bean {

    private static final long serialVersionUID = 8735813464998155513L;

    // region 主键

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // endregion

    // region 外键

    @Column(name = "category_id")
    private String categoryStringId;

    // endregion

    // region 主属性字段

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    // endregion

    // region 多对一

    @ManyToOne(targetEntity = HibernateAuditCategory.class)
    @JoinColumns({ //
            @JoinColumn(name = "category_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateAuditCategory auditCategory;

    // endregion

    // region 一对多

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateAuditEntryProperty.class, mappedBy = "auditEntry")
    private Set<HibernateAuditEntryProperty> auditEntryPropertySet = new HashSet<>();

    // endregion

    public HibernateAuditEntry() {
    }

    // region 映射用属性区

    public HibernateLongIdKey getKey() {
        return Optional.ofNullable(longId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setKey(HibernateLongIdKey key) {
        this.longId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public HibernateStringIdKey getCategoryKey() {
        return Optional.ofNullable(categoryStringId).map(HibernateStringIdKey::new).orElse(null);
    }

    public void setCategoryKey(HibernateStringIdKey key) {
        this.categoryStringId = Optional.ofNullable(key).map(HibernateStringIdKey::getStringId).orElse(null);
    }

    // endregion

    // region 常规属性区

    public Long getLongId() {
        return longId;
    }

    public void setLongId(Long longId) {
        this.longId = longId;
    }

    public String getCategoryStringId() {
        return categoryStringId;
    }

    public void setCategoryStringId(String categoryStringId) {
        this.categoryStringId = categoryStringId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public HibernateAuditCategory getAuditCategory() {
        return auditCategory;
    }

    public void setAuditCategory(HibernateAuditCategory auditCategory) {
        this.auditCategory = auditCategory;
    }

    public Set<HibernateAuditEntryProperty> getAuditEntryPropertySet() {
        return auditEntryPropertySet;
    }

    public void setAuditEntryPropertySet(Set<HibernateAuditEntryProperty> auditEntryPropertySet) {
        this.auditEntryPropertySet = auditEntryPropertySet;
    }

    // endregion

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "longId = " + longId + ", " +
                "categoryStringId = " + categoryStringId + ", " +
                "createdDate = " + createdDate + ", " +
                "auditCategory = " + auditCategory + ")";
    }
}
