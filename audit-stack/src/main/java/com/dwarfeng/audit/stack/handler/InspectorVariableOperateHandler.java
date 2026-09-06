package com.dwarfeng.audit.stack.handler;

import com.dwarfeng.audit.stack.bean.dto.InspectorVariableInspectInfo;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableInspectResult;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableRemoveInfo;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableUpsertInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import javax.annotation.Nullable;

/**
 * 审计器变量操作处理器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface InspectorVariableOperateHandler extends Handler {

    /**
     * 查看审计器变量。
     *
     * <p>
     * 该方法返回指定的审计器变量查看信息对应的审计器变量的查看结果。<br>
     * 如果指定的审计器变量不存在，则返回 <code>null</code>。
     *
     * @param info 审计器变量查看信息。
     * @return 审计器变量的查看结果。
     * @throws HandlerException 处理器异常。
     */
    @Nullable
    InspectorVariableInspectResult inspect(InspectorVariableInspectInfo info) throws HandlerException;

    /**
     * 插入/更新审计器变量。
     *
     * @param info 审计器变量插入/更新信息。
     * @throws HandlerException 处理器异常。
     */
    void upsert(InspectorVariableUpsertInfo info) throws HandlerException;

    /**
     * 删除审计器变量。
     *
     * @param info 审计器变量删除信息。
     * @throws HandlerException 处理器异常。
     */
    void remove(InspectorVariableRemoveInfo info) throws HandlerException;
}
