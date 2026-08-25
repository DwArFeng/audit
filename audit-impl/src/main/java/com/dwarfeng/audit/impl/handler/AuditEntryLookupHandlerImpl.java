package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.stack.bean.dto.AuditEntryCompositeLookupInfo;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryGroupedLookupInfo;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryLookupResult;
import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.audit.stack.handler.AuditEntryLookupHandler;
import com.dwarfeng.audit.stack.service.AuditEntryMaintainService;
import com.dwarfeng.subgrade.impl.service.PagingFixHelper;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

/**
 * 审计条目查询处理器实现。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
@Component
public class AuditEntryLookupHandlerImpl implements AuditEntryLookupHandler {

    private final HandlerValidator handlerValidator;
    private final AuditEntryMaintainService auditEntryMaintainService;

    public AuditEntryLookupHandlerImpl(
            HandlerValidator handlerValidator, AuditEntryMaintainService auditEntryMaintainService
    ) {
        this.handlerValidator = handlerValidator;
        this.auditEntryMaintainService = auditEntryMaintainService;
    }

    @Override
    @BehaviorAnalyse
    public AuditEntryLookupResult lookupComposite(AuditEntryCompositeLookupInfo info) throws HandlerException {
        try {
            handlerValidator.makeSureAuditEntryCompositeLookupInfoValid(info);
            PagingInfo pagingInfo = PagingFixHelper.mayFixPagingInfo(info.getPagingInfo());
            PagedData<AuditEntry> pagedData = auditEntryMaintainService.lookup(
                    AuditEntryMaintainService.COMPOSITE_LOOKUP, new Object[]{info}, pagingInfo
            );
            return toResult(pagedData);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Override
    @BehaviorAnalyse
    public AuditEntryLookupResult lookupGrouped(AuditEntryGroupedLookupInfo info) throws HandlerException {
        try {
            handlerValidator.makeSureAuditEntryGroupedLookupInfoValid(info);
            PagingInfo pagingInfo = PagingFixHelper.mayFixPagingInfo(info.getPagingInfo());
            PagedData<AuditEntry> pagedData = auditEntryMaintainService.lookup(
                    AuditEntryMaintainService.GROUPED_LOOKUP, new Object[]{info}, pagingInfo
            );
            return toResult(pagedData);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private AuditEntryLookupResult toResult(PagedData<AuditEntry> pagedData) {
        return new AuditEntryLookupResult(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                pagedData.getData()
        );
    }
}
