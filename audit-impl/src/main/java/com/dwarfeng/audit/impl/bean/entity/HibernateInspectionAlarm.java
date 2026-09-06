package com.dwarfeng.audit.impl.bean.entity;

import com.dwarfeng.audit.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_inspection_alarm")
public class HibernateInspectionAlarm implements Bean {

    private static final long serialVersionUID = -6187860986607549402L;

    // region 主键

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // endregion

    // region 外键

    @Column(name = "inspection_id")
    private Long inspectionLongId;

    @Column(name = "inspection_task_id")
    private Long inspectionTaskLongId;

    @Column(name = "inspector_info_id")
    private Long inspectorInfoLongId;

    // endregion

    // region 主属性字段

    @Column(name = "happened_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date happenedDate;

    @Column(name = "type", length = Constraints.LENGTH_TYPE)
    private String type;

    @Column(name = "message", length = Constraints.LENGTH_MESSAGE)
    private String message;

    // endregion

    // region 多对一

    @ManyToOne(targetEntity = HibernateInspection.class)
    @JoinColumns({ //
            @JoinColumn(name = "inspection_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateInspection inspection;

    @ManyToOne(targetEntity = HibernateInspectionTask.class)
    @JoinColumns({ //
            @JoinColumn(name = "inspection_task_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateInspectionTask inspectionTask;

    @ManyToOne(targetEntity = HibernateInspectorInfo.class)
    @JoinColumns({ //
            @JoinColumn(name = "inspector_info_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateInspectorInfo inspectorInfo;

    // endregion

    public HibernateInspectionAlarm() {
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

    public HibernateLongIdKey getInspectionTaskKey() {
        return Optional.ofNullable(inspectionTaskLongId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setInspectionTaskKey(HibernateLongIdKey key) {
        this.inspectionTaskLongId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public HibernateLongIdKey getInspectorInfoKey() {
        return Optional.ofNullable(inspectorInfoLongId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setInspectorInfoKey(HibernateLongIdKey key) {
        this.inspectorInfoLongId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
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

    public Long getInspectionTaskLongId() {
        return inspectionTaskLongId;
    }

    public void setInspectionTaskLongId(Long inspectionTaskLongId) {
        this.inspectionTaskLongId = inspectionTaskLongId;
    }

    public Long getInspectorInfoLongId() {
        return inspectorInfoLongId;
    }

    public void setInspectorInfoLongId(Long inspectorInfoLongId) {
        this.inspectorInfoLongId = inspectorInfoLongId;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HibernateInspection getInspection() {
        return inspection;
    }

    public void setInspection(HibernateInspection inspection) {
        this.inspection = inspection;
    }

    public HibernateInspectionTask getInspectionTask() {
        return inspectionTask;
    }

    public void setInspectionTask(HibernateInspectionTask inspectionTask) {
        this.inspectionTask = inspectionTask;
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
                "longId = " + longId + ", " +
                "inspectionLongId = " + inspectionLongId + ", " +
                "inspectionTaskLongId = " + inspectionTaskLongId + ", " +
                "inspectorInfoLongId = " + inspectorInfoLongId + ", " +
                "happenedDate = " + happenedDate + ", " +
                "type = " + type + ", " +
                "message = " + message + ", " +
                "inspection = " + inspection + ", " +
                "inspectionTask = " + inspectionTask + ", " +
                "inspectorInfo = " + inspectorInfo + ")";
    }
}
