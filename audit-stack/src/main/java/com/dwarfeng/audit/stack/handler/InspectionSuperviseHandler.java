package com.dwarfeng.audit.stack.handler;

import com.dwarfeng.subgrade.stack.handler.DistributedLockHandler;

/**
 * 自动审计主管处理器。
 *
 * <p>
 * 该处理器用于管理自动审计任务的驱动和调度。
 *
 * <p>
 * 在审计服务集群中，最多只有一个自动审计主管处理器在运行；发生故障时由集群自动选举新的自动审计主管处理器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectionSuperviseHandler extends DistributedLockHandler {
}
