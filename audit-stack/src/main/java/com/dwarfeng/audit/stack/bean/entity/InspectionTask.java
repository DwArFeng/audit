package com.dwarfeng.audit.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * 自动审计任务。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class InspectionTask implements Entity<LongIdKey> {

    private static final long serialVersionUID = -846510577242794462L;

    private LongIdKey key;
    private LongIdKey inspectionKey;

    /**
     * 任务状态。
     *
     * <p>
     * int 枚举，可能的状态为：
     * <ol>
     *     <li>任务创建</li>
     *     <li>任务进行</li>
     *     <li>任务完成</li>
     *     <li>任务失败</li>
     *     <li>任务过期</li>
     *     <li>任务死亡</li>
     * </ol>
     * 详细值参考 sdk 模块的常量工具类。
     */
    private int status;

    private Date createdDate;
    private Date startedDate;
    private Date endedDate;
    private Long duration;
    private Date shouldExpireDate;
    private Date shouldDieDate;
    private Date expiredDate;
    private Date diedDate;
    private String anchorMessage;

    public InspectionTask() {
    }

    public InspectionTask(
            LongIdKey key, LongIdKey inspectionKey, int status, Date createdDate, Date startedDate, Date endedDate,
            Long duration, Date shouldExpireDate, Date shouldDieDate, Date expiredDate, Date diedDate,
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

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getInspectionKey() {
        return inspectionKey;
    }

    public void setInspectionKey(LongIdKey inspectionKey) {
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
        return "InspectionTask{" +
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
