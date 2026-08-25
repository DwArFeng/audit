package com.dwarfeng.audit.impl.service;

import com.dwarfeng.audit.stack.bean.dto.AuditEntryCompositeLookupInfo;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryGroupedLookupInfo;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryLookupResult;
import com.dwarfeng.audit.stack.handler.AuditEntryLookupHandler;
import com.dwarfeng.audit.stack.service.AuditEntryLookupQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Component;

@Component
public class AuditEntryLookupQosServiceImpl implements AuditEntryLookupQosService {

    private final AuditEntryLookupHandler auditEntryLookupHandler;
    private final ServiceExceptionMapper sem;

    public AuditEntryLookupQosServiceImpl(
            AuditEntryLookupHandler auditEntryLookupHandler, ServiceExceptionMapper sem
    ) {
        this.auditEntryLookupHandler = auditEntryLookupHandler;
        this.sem = sem;
    }

    @Override
    public AuditEntryLookupResult lookupComposite(AuditEntryCompositeLookupInfo info) throws ServiceException {
        try {
            return auditEntryLookupHandler.lookupComposite(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("组合查询审计条目时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public AuditEntryLookupResult lookupGrouped(AuditEntryGroupedLookupInfo info) throws ServiceException {
        try {
            return auditEntryLookupHandler.lookupGrouped(info);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("分组查询审计条目时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
