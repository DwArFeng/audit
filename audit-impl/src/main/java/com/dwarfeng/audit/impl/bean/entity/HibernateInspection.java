package com.dwarfeng.audit.impl.bean.entity;

import com.dwarfeng.audit.sdk.util.Constraints;
import com.dwarfeng.datamark.sdk.jpa.DatamarkEntityListener;
import com.dwarfeng.datamark.sdk.jpa.DatamarkField;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_inspection")
@EntityListeners(DatamarkEntityListener.class)
public class HibernateInspection implements Bean {

    private static final long serialVersionUID = 7453373165598092507L;

    // region 主键

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // endregion

    // region 主属性字段

    @Column(name = "name", length = Constraints.LENGTH_NAME, nullable = false)
    private String name;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // endregion

    // region 一对多

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateInspectionAlarm.class, mappedBy = "inspection")
    private Set<HibernateInspectionAlarm> inspectionAlarmSet = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateInspectionDriverInfo.class, mappedBy = "inspection")
    private Set<HibernateInspectionDriverInfo> inspectionDriverInfoSet = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateInspectionTask.class, mappedBy = "inspection")
    private Set<HibernateInspectionTask> inspectionTaskSet = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateInspectorInfo.class, mappedBy = "inspection")
    private Set<HibernateInspectorInfo> inspectorInfoSet = new HashSet<>();

    // endregion

    // region 审计

    @DatamarkField(handlerName = "inspectionDatamarkHandler")
    @Column(
            name = "created_datamark",
            length = com.dwarfeng.datamark.sdk.util.Constraints.LENGTH_DATAMARK_VALUE,
            updatable = false
    )
    private String createdDatamark;

    @DatamarkField(handlerName = "inspectionDatamarkHandler")
    @Column(
            name = "modified_datamark",
            length = com.dwarfeng.datamark.sdk.util.Constraints.LENGTH_DATAMARK_VALUE
    )
    private String modifiedDatamark;

    // endregion

    public HibernateInspection() {
    }

    // region 映射用属性区

    public HibernateLongIdKey getKey() {
        return Optional.ofNullable(longId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setKey(HibernateLongIdKey key) {
        this.longId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    // endregion

    // region 常规属性区

    public Long getLongId() {
        return longId;
    }

    public void setLongId(Long longId) {
        this.longId = longId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Set<HibernateInspectionAlarm> getInspectionAlarmSet() {
        return inspectionAlarmSet;
    }

    public void setInspectionAlarmSet(Set<HibernateInspectionAlarm> inspectionAlarmSet) {
        this.inspectionAlarmSet = inspectionAlarmSet;
    }

    public Set<HibernateInspectionDriverInfo> getInspectionDriverInfoSet() {
        return inspectionDriverInfoSet;
    }

    public void setInspectionDriverInfoSet(Set<HibernateInspectionDriverInfo> inspectionDriverInfoSet) {
        this.inspectionDriverInfoSet = inspectionDriverInfoSet;
    }

    public Set<HibernateInspectionTask> getInspectionTaskSet() {
        return inspectionTaskSet;
    }

    public void setInspectionTaskSet(Set<HibernateInspectionTask> inspectionTaskSet) {
        this.inspectionTaskSet = inspectionTaskSet;
    }

    public Set<HibernateInspectorInfo> getInspectorInfoSet() {
        return inspectorInfoSet;
    }

    public void setInspectorInfoSet(Set<HibernateInspectorInfo> inspectorInfoSet) {
        this.inspectorInfoSet = inspectorInfoSet;
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
                "longId = " + longId + ", " +
                "name = " + name + ", " +
                "enabled = " + enabled + ", " +
                "remark = " + remark + ", " +
                "createdDatamark = " + createdDatamark + ", " +
                "modifiedDatamark = " + modifiedDatamark + ")";
    }
}
