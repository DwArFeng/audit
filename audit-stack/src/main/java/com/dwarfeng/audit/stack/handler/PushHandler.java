package com.dwarfeng.audit.stack.handler;

import com.dwarfeng.audit.stack.bean.dto.PurgeFinishedResult;
import com.dwarfeng.audit.stack.bean.entity.InspectionAlarm;
import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 推送处理器。
 *
 * <p>
 * 该处理器负责将审计服务内部发生的业务事件转换为外部可消费的推送事件。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public interface PushHandler extends Handler {

    /**
     * 审核记录功能重置时执行的推送操作。
     *
     * @throws HandlerException 处理器异常。
     */
    void auditRecordReset() throws HandlerException;

    /**
     * 自动审计主管功能重置时执行的推送操作。
     *
     * @throws HandlerException 处理器异常。
     * @since 1.1.0
     */
    void inspectionSuperviseReset() throws HandlerException;

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

    /**
     * 清除任务完成时执行的推送操作。
     *
     * @param result 清除任务完成结果。
     * @throws HandlerException 处理器异常。
     */
    void purgeFinished(PurgeFinishedResult result) throws HandlerException;

    /**
     * 清除任务失败时执行的推送操作。
     *
     * @throws HandlerException 处理器异常。
     */
    void purgeFailed() throws HandlerException;
}
