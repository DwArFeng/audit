package com.dwarfeng.audit.stack.handler;

import com.dwarfeng.audit.stack.bean.dto.InspectionJobCreateInfo;
import com.dwarfeng.audit.stack.bean.dto.InspectionJobCreateResult;
import com.dwarfeng.audit.stack.bean.dto.InspectionJobExecuteInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.concurrent.CompletableFuture;

/**
 * 自动审计作业处理器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionJobHandler extends Handler {

    /**
     * 创建自动审计作业。
     *
     * @param info 自动审计作业创建信息。
     * @return 自动审计作业创建结果。
     * @throws HandlerException 处理器异常。
     */
    InspectionJobCreateResult create(InspectionJobCreateInfo info) throws HandlerException;

    /**
     * 同步执行自动审计作业。
     *
     * @param info 自动审计作业执行信息。
     * @throws HandlerException 处理器异常。
     */
    void execute(InspectionJobExecuteInfo info) throws HandlerException;

    /**
     * 异步执行自动审计作业。
     *
     * @param info 自动审计作业执行信息。
     * @return 作业执行结果对应的 CompletableFuture。
     */
    CompletableFuture<Void> executeAsync(InspectionJobExecuteInfo info);
}
