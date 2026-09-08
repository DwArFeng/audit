package com.dwarfeng.audit.sdk.handler;

import com.dwarfeng.audit.stack.bean.entity.InspectionAlarm;
import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 事件推送器。
 *
 * <p>
 * 推送器是推送机制的可插拔实现。每个推送器通过类型标识参与装配，由推送处理器选择当前配置的具体实现。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public interface Pusher {

    /**
     * 返回推送器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 推送器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 审核记录功能重置时执行的推送操作。
     *
     * @throws HandlerException 处理器异常。
     */
    void auditRecordReset() throws HandlerException;

    /**
     * 自动审计任务完成时执行的推送操作。
     *
     * @param inspectionTask 已完成的自动审计任务。
     * @throws HandlerException 处理器异常。
     * @since 1.1.0
     */
    void inspectionTaskFinished(InspectionTask inspectionTask) throws HandlerException;

    /**
     * 自动审计任务失败时执行的推送操作。
     *
     * @param inspectionTask 已失败的自动审计任务。
     * @throws HandlerException 处理器异常。
     * @since 1.1.0
     */
    void inspectionTaskFailed(InspectionTask inspectionTask) throws HandlerException;

    /**
     * 自动审计任务过期时执行的推送操作。
     *
     * @param inspectionTask 已过期的自动审计任务。
     * @throws HandlerException 处理器异常。
     * @since 1.1.0
     */
    void inspectionTaskExpired(InspectionTask inspectionTask) throws HandlerException;

    /**
     * 自动审计任务死亡时执行的推送操作。
     *
     * @param inspectionTask 已死亡的自动审计任务。
     * @throws HandlerException 处理器异常。
     * @since 1.1.0
     */
    void inspectionTaskDied(InspectionTask inspectionTask) throws HandlerException;

    /**
     * 自动审计作业功能重置时执行的推送操作。
     *
     * @throws HandlerException 处理器异常。
     * @since 1.1.0
     */
    void inspectionJobReset() throws HandlerException;

    /**
     * 自动审计报警创建时执行的推送操作。
     *
     * @param inspectionAlarm 创建的自动审计报警。
     * @throws HandlerException 处理器异常。
     * @since 1.1.0
     */
    void inspectionAlarmCreated(InspectionAlarm inspectionAlarm) throws HandlerException;
}
