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
@Table(name = "tbl_inspector_info")
@EntityListeners(DatamarkEntityListener.class)
public class HibernateInspectorInfo implements Bean {

    private static final long serialVersionUID = 3665873027984647982L;

    // region 主键

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // endregion

    // region 外键

    @Column(name = "inspection_id")
    private Long inspectionLongId;

    // endregion

    // region 主属性字段

    @Column(name = "column_index", nullable = false)
    private int index;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "type", length = Constraints.LENGTH_TYPE, nullable = false)
    private String type;

    @Column(name = "param", columnDefinition = "TEXT")
    private String param;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // endregion

    // region 多对一

    @ManyToOne(targetEntity = HibernateInspection.class)
    @JoinColumns({ //
            @JoinColumn(name = "inspection_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateInspection inspection;

    // endregion

    // region 一对多

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateInspectionAlarm.class, mappedBy = "inspectorInfo")
    private Set<HibernateInspectionAlarm> inspectionAlarmSet = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateInspectorVariable.class, mappedBy = "inspectorInfo")
    private Set<HibernateInspectorVariable> inspectorVariableSet = new HashSet<>();

    // endregion

    // region 审计

    @DatamarkField(handlerName = "inspectorDatamarkHandler")
    @Column(
            name = "created_datamark",
            length = com.dwarfeng.datamark.sdk.util.Constraints.LENGTH_DATAMARK_VALUE,
            updatable = false
    )
    private String createdDatamark;

    @DatamarkField(handlerName = "inspectorDatamarkHandler")
    @Column(
            name = "modified_datamark",
            length = com.dwarfeng.datamark.sdk.util.Constraints.LENGTH_DATAMARK_VALUE
    )
    private String modifiedDatamark;

    // endregion

    public HibernateInspectorInfo() {
    }

    // region 映射用属性区

    public HibernateLongIdKey getKey() {
        return Optional.ofNullable(longId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setKey(HibernateLongIdKey key) {
        this.longId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public HibernateLongIdKey getInspectionKey() {
        return Optional.ofNullable(inspectionLongId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setInspectionKey(HibernateLongIdKey key) {
        this.inspectionLongId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    // endregion

    // region 常规属性区

    public Long getLongId() {
        return longId;
    }

    public void setLongId(Long longId) {
        this.longId = longId;
    }

    public Long getInspectionLongId() {
        return inspectionLongId;
    }

    public void setInspectionLongId(Long inspectionLongId) {
        this.inspectionLongId = inspectionLongId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HibernateInspection getInspection() {
        return inspection;
    }

    public void setInspection(HibernateInspection inspection) {
        this.inspection = inspection;
    }

    public Set<HibernateInspectionAlarm> getInspectionAlarmSet() {
        return inspectionAlarmSet;
    }

    public void setInspectionAlarmSet(Set<HibernateInspectionAlarm> inspectionAlarmSet) {
        this.inspectionAlarmSet = inspectionAlarmSet;
    }

    public Set<HibernateInspectorVariable> getInspectorVariableSet() {
        return inspectorVariableSet;
    }

    public void setInspectorVariableSet(Set<HibernateInspectorVariable> inspectorVariableSet) {
        this.inspectorVariableSet = inspectorVariableSet;
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
                "inspectionLongId = " + inspectionLongId + ", " +
                "index = " + index + ", " +
                "enabled = " + enabled + ", " +
                "type = " + type + ", " +
                "param = " + param + ", " +
                "remark = " + remark + ", " +
                "inspection = " + inspection + ", " +
                "createdDatamark = " + createdDatamark + ", " +
                "modifiedDatamark = " + modifiedDatamark + ")";
    }
}
