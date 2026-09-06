package com.dwarfeng.audit.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 自动审计任务。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class FastJsonInspectionTask implements Bean {

    private static final long serialVersionUID = 9167875317697560449L;

    public static FastJsonInspectionTask of(InspectionTask inspectionTask) {
        if (Objects.isNull(inspectionTask)) {
            return null;
        } else {
            return new FastJsonInspectionTask(
                    FastJsonLongIdKey.of(inspectionTask.getKey()),
                    FastJsonLongIdKey.of(inspectionTask.getInspectionKey()),
                    inspectionTask.getStatus(),
                    inspectionTask.getCreatedDate(),
                    inspectionTask.getStartedDate(),
                    inspectionTask.getEndedDate(),
                    inspectionTask.getDuration(),
                    inspectionTask.getShouldExpireDate(),
                    inspectionTask.getShouldDieDate(),
                    inspectionTask.getExpiredDate(),
                    inspectionTask.getDiedDate(),
                    inspectionTask.getAnchorMessage()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "inspection_key", ordinal = 2)
    private FastJsonLongIdKey inspectionKey;

    @JSONField(name = "status", ordinal = 3)
    private int status;

    @JSONField(name = "created_date", ordinal = 4)
    private Date createdDate;

    @JSONField(name = "started_date", ordinal = 5)
    private Date startedDate;

    @JSONField(name = "ended_date", ordinal = 6)
    private Date endedDate;

    @JSONField(name = "duration", ordinal = 7)
    private Long duration;

    @JSONField(name = "should_expire_date", ordinal = 8)
    private Date shouldExpireDate;

    @JSONField(name = "should_die_date", ordinal = 9)
    private Date shouldDieDate;

    @JSONField(name = "expired_date", ordinal = 10)
    private Date expiredDate;

    @JSONField(name = "died_date", ordinal = 11)
    private Date diedDate;

    @JSONField(name = "anchor_message", ordinal = 12)
    private String anchorMessage;

    public FastJsonInspectionTask() {
    }

    public FastJsonInspectionTask(
            FastJsonLongIdKey key, FastJsonLongIdKey inspectionKey, int status, Date createdDate, Date startedDate,
            Date endedDate, Long duration, Date shouldExpireDate, Date shouldDieDate, Date expiredDate, Date diedDate,
            String anchorMessage
    ) {
        this.key = key;
        this.inspectionKey = inspectionKey;
        this.status = status;
        this.createdDate = createdDate;
        this.startedDate = startedDate;
        this.endedDate = endedDate;
        this.duration = duration;
        this.shouldExpireDate = shouldExpireDate;
        this.shouldDieDate = shouldDieDate;
        this.expiredDate = expiredDate;
        this.diedDate = diedDate;
        this.anchorMessage = anchorMessage;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
        this.key = key;
    }

    public FastJsonLongIdKey getInspectionKey() {
        return inspectionKey;
    }

    public void setInspectionKey(FastJsonLongIdKey inspectionKey) {
        this.inspectionKey = inspectionKey;
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

    @Override
    public String toString() {
        return "FastJsonInspectionTask{" +
                "key=" + key +
                ", inspectionKey=" + inspectionKey +
                ", status=" + status +
                ", createdDate=" + createdDate +
                ", startedDate=" + startedDate +
                ", endedDate=" + endedDate +
                ", duration=" + duration +
                ", shouldExpireDate=" + shouldExpireDate +
                ", shouldDieDate=" + shouldDieDate +
                ", expiredDate=" + expiredDate +
                ", diedDate=" + diedDate +
                ", anchorMessage='" + anchorMessage + '\'' +
                '}';
    }
}
