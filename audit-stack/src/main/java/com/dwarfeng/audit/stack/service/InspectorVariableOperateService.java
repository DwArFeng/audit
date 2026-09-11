package com.dwarfeng.audit.stack.service;

import com.dwarfeng.audit.stack.bean.dto.InspectorVariableInspectInfo;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableInspectResult;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableRemoveInfo;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableUpsertInfo;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import javax.annotation.Nullable;

/**
 * 审计器变量操作服务。
 *
 * @author DwArFeng
 * @since 1.1.1
 */
public interface InspectorVariableOperateService extends Service {

    /**
     * 查看审计器变量。
     *
     * <p>
     * 该方法返回指定的审计器变量查看信息对应的审计器变量的查看结果。<br>
     * 如果指定的审计器变量不存在，则返回 <code>null</code>。
     *
     * @param info 审计器变量查看信息。
     * @return 审计器变量的查看结果。
     * @throws ServiceException 服务异常。
     */
    @Nullable
    InspectorVariableInspectResult inspect(InspectorVariableInspectInfo info) throws ServiceException;

    /**
     * 插入/更新审计器变量。
     *
     * @param info 审计器变量插入/更新信息。
     * @throws ServiceException 服务异常。
     */
    void upsert(InspectorVariableUpsertInfo info) throws ServiceException;

    /**
     * 删除审计器变量。
     *
     * @param info 审计器变量删除信息。
     * @throws ServiceException 服务异常。
     */
    void remove(InspectorVariableRemoveInfo info) throws ServiceException;
}
