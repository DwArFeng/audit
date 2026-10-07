package com.dwarfeng.audit.impl.bean.entity;

import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_inspection_task")
public class HibernateInspectionTask implements Bean {

    private static final long serialVersionUID = 7506876465182158368L;

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

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "started_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startedDate;

    @Column(name = "ended_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endedDate;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "should_expire_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date shouldExpireDate;

    @Column(name = "should_die_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date shouldDieDate;

    @Column(name = "expired_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredDate;

    @Column(name = "died_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date diedDate;

    @Column(name = "anchor_message", columnDefinition = "TEXT")
    private String anchorMessage;

    // endregion

    // region 多对一

    @ManyToOne(targetEntity = HibernateInspection.class)
    @JoinColumns({ //
            @JoinColumn(name = "inspection_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateInspection inspection;

    // endregion

    // region 一对多

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateInspectionAlarm.class, mappedBy = "inspectionTask")
    private Set<HibernateInspectionAlarm> inspectionAlarmSet = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateInspectionTaskEvent.class, mappedBy = "inspectionTask")
    private Set<HibernateInspectionTaskEvent> inspectionTaskEventSet = new HashSet<>();

    // endregion

    public HibernateInspectionTask() {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(Date startedDate) {
        this.startedDate = startedDate;
    }

    public Date getEndedDate() {
        return endedDate;
    }

    public void setEndedDate(Date endedDate) {
        this.endedDate = endedDate;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Date getShouldExpireDate() {
        return shouldExpireDate;
    }

    public void setShouldExpireDate(Date shouldExpireDate) {
        this.shouldExpireDate = shouldExpireDate;
    }

    public Date getShouldDieDate() {
        return shouldDieDate;
    }

    public void setShouldDieDate(Date shouldDieDate) {
        this.shouldDieDate = shouldDieDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Date getDiedDate() {
        return diedDate;
    }

    public void setDiedDate(Date diedDate) {
        this.diedDate = diedDate;
    }

    public String getAnchorMessage() {
        return anchorMessage;
    }

    public void setAnchorMessage(String anchorMessage) {
        this.anchorMessage = anchorMessage;
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

    public Set<HibernateInspectionTaskEvent> getInspectionTaskEventSet() {
        return inspectionTaskEventSet;
    }

    public void setInspectionTaskEventSet(Set<HibernateInspectionTaskEvent> inspectionTaskEventSet) {
        this.inspectionTaskEventSet = inspectionTaskEventSet;
    }

    // endregion

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "longId = " + longId + ", " +
                "inspectionLongId = " + inspectionLongId + ", " +
                "status = " + status + ", " +
                "createdDate = " + createdDate + ", " +
                "startedDate = " + startedDate + ", " +
                "endedDate = " + endedDate + ", " +
                "duration = " + duration + ", " +
                "shouldExpireDate = " + shouldExpireDate + ", " +
                "shouldDieDate = " + shouldDieDate + ", " +
                "expiredDate = " + expiredDate + ", " +
                "diedDate = " + diedDate + ", " +
                "anchorMessage = " + anchorMessage + ", " +
                "inspection = " + inspection + ")";
    }
}
