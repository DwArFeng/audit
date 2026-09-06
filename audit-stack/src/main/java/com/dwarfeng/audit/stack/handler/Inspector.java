package com.dwarfeng.audit.stack.handler;

import com.dwarfeng.audit.stack.bean.dto.*;
import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.audit.stack.bean.entity.InspectorInfo;
import com.dwarfeng.audit.stack.exception.InspectorException;

import javax.annotation.Nullable;

/**
 * 审计器。
 *
 * <p>
 * 审计器用于检查指定自动审计任务对应的审计记录，并可以通过 {@link Context} 查询审计记录、维护审计器变量
 * 以及创建自动审计报警。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface Inspector {

    /**
     * 生成一个新的审计器执行器。
     *
     * @return 新的审计器执行器。
     * @throws InspectorException 审计器异常。
     */
    Executor newExecutor() throws InspectorException;

    /**
     * 审计器执行器。
     *
     * @author DwArFeng
     * @since 1.1.0
     */
    interface Executor {

        /**
         * 初始化审计器执行器。
         *
         * @param context 指定的审计器上下文。
         */
        void init(Context context);

        /**
         * 执行审计检查。
         *
         * <p>
         * 该方法被调用时，需要按照预定的逻辑检查审计记录，并通过 {@link Context} 获取审计上下文及操作服务。
         *
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        void inspect() throws Exception;
    }

    /**
     * 审计器上下文。
     *
     * @author DwArFeng
     * @since 1.1.0
     */
    interface Context {

        /**
         * 获取自动审计。
         *
         * @return 自动审计。
         */
        Inspection getInspection();

        /**
         * 获取自动审计任务。
         *
         * @return 自动审计任务。
         */
        InspectionTask getInspectionTask();

        /**
         * 获取审计器信息。
         *
         * @return 审计器信息。
         */
        InspectorInfo getInspectorInfo();

        /**
         * 执行组合查询。
         *
         * @param info 组合查询信息。
         * @return 查询结果。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        AuditEntryLookupResult lookupComposite(AuditEntryCompositeLookupInfo info) throws Exception;

        /**
         * 执行分组查询。
         *
         * @param info 分组查询信息。
         * @return 查询结果。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        AuditEntryLookupResult lookupGrouped(AuditEntryGroupedLookupInfo info) throws Exception;

        /**
         * 创建自动审计报警。
         *
         * @param info 自动审计报警创建信息。
         * @return 自动审计报警创建结果。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        InspectionAlarmCreateResult createInspectionAlarm(InspectionAlarmCreateInfo info) throws Exception;

        /**
         * 查看审计器变量。
         *
         * <p>
         * 如果指定的审计器变量不存在，则返回 <code>null</code>。
         *
         * @param info 审计器变量查看信息。
         * @return 审计器变量查看结果。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        @Nullable
        InspectorVariableInspectResult inspectInspectorVariable(InspectorVariableInspectInfo info) throws Exception;

        /**
         * 插入/更新审计器变量。
         *
         * @param info 审计器变量插入/更新信息。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        void upsertInspectorVariable(InspectorVariableUpsertInfo info) throws Exception;

        /**
         * 删除审计器变量。
         *
         * @param info 审计器变量删除信息。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        void removeInspectorVariable(InspectorVariableRemoveInfo info) throws Exception;
    }
}
