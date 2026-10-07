package com.dwarfeng.audit.impl.bean.entity;

import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_inspection_task_event")
public class HibernateInspectionTaskEvent implements Bean {

    private static final long serialVersionUID = 6883420811855523577L;

    // region 主键

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // endregion

    // region 外键

    @Column(name = "inspection_task_id")
    private Long inspectionTaskLongId;

    // endregion

    // region 主属性字段

    @Column(name = "happened_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date happenedDate;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    // endregion

    // region 多对一

    @ManyToOne(targetEntity = HibernateInspectionTask.class)
    @JoinColumns({ //
            @JoinColumn(name = "inspection_task_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateInspectionTask inspectionTask;

    // endregion

    public HibernateInspectionTaskEvent() {
    }

    // region 映射用属性区

    public HibernateLongIdKey getKey() {
        return Optional.ofNullable(longId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setKey(HibernateLongIdKey key) {
        this.longId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public HibernateLongIdKey getInspectionTaskKey() {
        return Optional.ofNullable(inspectionTaskLongId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setInspectionTaskKey(HibernateLongIdKey key) {
        this.inspectionTaskLongId = Optional.ofNullable(key).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    // endregion

    // region 常规属性区

    public Long getLongId() {
        return longId;
    }

    public void setLongId(Long longId) {
        this.longId = longId;
    }

    public Long getInspectionTaskLongId() {
        return inspectionTaskLongId;
    }

    public void setInspectionTaskLongId(Long inspectionTaskLongId) {
        this.inspectionTaskLongId = inspectionTaskLongId;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HibernateInspectionTask getInspectionTask() {
        return inspectionTask;
    }

    public void setInspectionTask(HibernateInspectionTask inspectionTask) {
        this.inspectionTask = inspectionTask;
    }

    // endregion

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "longId = " + longId + ", " +
                "inspectionTaskLongId = " + inspectionTaskLongId + ", " +
                "happenedDate = " + happenedDate + ", " +
                "message = " + message + ", " +
                "inspectionTask = " + inspectionTask + ")";
    }
}
