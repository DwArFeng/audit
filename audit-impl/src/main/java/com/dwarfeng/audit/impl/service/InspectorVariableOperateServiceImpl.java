package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.bean.dto.InspectorVariableInspectInfo;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableInspectResult;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableRemoveInfo;
import com.dwarfeng.audit.stack.bean.dto.InspectorVariableUpsertInfo;
import com.dwarfeng.audit.stack.handler.InspectorVariableOperateHandler;
import com.dwarfeng.audit.stack.service.InspectorVariableOperateService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

/**
 * 审计器变量操作服务实现。
 *
 * @author DwArFeng
 * @since 1.1.1
 */
@Service
public class InspectorVariableOperateServiceImpl implements InspectorVariableOperateService {

    private final InspectorVariableOperateHandler inspectorVariableOperateHandler;
    private final ServiceExceptionMapper sem;

    public InspectorVariableOperateServiceImpl(
            InspectorVariableOperateHandler inspectorVariableOperateHandler, ServiceExceptionMapper sem
    ) {
        this.inspectorVariableOperateHandler = inspectorVariableOperateHandler;
        this.sem = sem;
    }

    @Override
    @Nullable
    public InspectorVariableInspectResult inspect(InspectorVariableInspectInfo info) throws ServiceException {
        try {
            return inspectorVariableOperateHandler.inspect(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查看审计器变量时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void upsert(InspectorVariableUpsertInfo info) throws ServiceException {
        try {
            inspectorVariableOperateHandler.upsert(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("插入/更新审计器变量时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void remove(InspectorVariableRemoveInfo info) throws ServiceException {
        try {
            inspectorVariableOperateHandler.remove(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("删除审计器变量时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
