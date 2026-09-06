package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.stack.bean.dto.AuditEntryCompositeLookupInfo;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryGroupedLookupInfo;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryLookupResult;
import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.audit.stack.bean.entity.AuditEntryProperty;
import com.dwarfeng.audit.stack.handler.AuditEntryLookupHandler;
import com.dwarfeng.audit.stack.service.AuditCategoryMaintainService;
import com.dwarfeng.audit.stack.service.AuditEntryMaintainService;
import com.dwarfeng.audit.stack.service.AuditEntryPropertyMaintainService;
import com.dwarfeng.subgrade.impl.service.PagingFixHelper;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

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
    private final AuditEntryPropertyMaintainService auditEntryPropertyMaintainService;
    private final AuditCategoryMaintainService auditCategoryMaintainService;

    public AuditEntryLookupHandlerImpl(
            HandlerValidator handlerValidator,
            AuditEntryMaintainService auditEntryMaintainService,
            AuditEntryPropertyMaintainService auditEntryPropertyMaintainService,
            AuditCategoryMaintainService auditCategoryMaintainService
    ) {
        this.handlerValidator = handlerValidator;
        this.auditEntryMaintainService = auditEntryMaintainService;
        this.auditEntryPropertyMaintainService = auditEntryPropertyMaintainService;
        this.auditCategoryMaintainService = auditCategoryMaintainService;
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

    private AuditEntryLookupResult toResult(PagedData<AuditEntry> pagedData) throws Exception {
        List<AuditEntry> auditEntries = pagedData.getData();
        List<LongIdKey> auditEntryKeys = auditEntries.stream()
                .filter(Objects::nonNull)
                .map(AuditEntry::getKey)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<AuditEntryProperty> auditEntryProperties = auditEntryKeys.isEmpty() ?
                Collections.emptyList() : auditEntryPropertyMaintainService.lookupAsList(
                AuditEntryPropertyMaintainService.CHILD_FOR_AUDIT_ENTRIES,
                new Object[]{auditEntryKeys}
        );
        List<StringIdKey> categoryKeys = auditEntries.stream()
                .filter(Objects::nonNull)
                .map(AuditEntry::getCategoryKey)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<AuditCategory> auditCategories = categoryKeys.isEmpty() ?
                Collections.emptyList() : auditCategoryMaintainService.batchGet(categoryKeys);
        Map<String, AuditCategory> categoryMap = auditCategories.stream()
                .filter(category -> Objects.nonNull(category) && Objects.nonNull(category.getKey()))
                .collect(Collectors.toMap(
                        category -> category.getKey().getStringId(),
                        category -> category,
                        (left, right) -> left
                ));
        Map<Long, List<AuditEntryProperty>> propertyMap = auditEntryProperties.stream()
                .filter(property -> Objects.nonNull(property) && Objects.nonNull(property.getKey()))
                .collect(Collectors.groupingBy(property -> property.getKey().getAuditEntryLongId()));
        List<AuditEntryLookupResult.Data> data = auditEntries.stream()
                .map(auditEntry -> {
                    if (Objects.isNull(auditEntry) || Objects.isNull(auditEntry.getKey())) {
                        return new AuditEntryLookupResult.Data(auditEntry, null, new LinkedHashMap<>());
                    }
                    List<AuditEntryProperty> properties = propertyMap.getOrDefault(
                            auditEntry.getKey().getLongId(), Collections.emptyList()
                    );
                    Map<String, AuditEntryProperty> propertyDetailMap = properties.stream()
                            .filter(property -> Objects.nonNull(property) && Objects.nonNull(property.getKey()))
                            .collect(Collectors.toMap(
                                    property -> property.getKey().getPropertyStringId(),
                                    property -> property,
                                    (left, right) -> left,
                                    LinkedHashMap::new
                            ));
                    AuditCategory auditCategory = Objects.isNull(auditEntry.getCategoryKey()) ? null : categoryMap.get(
                            auditEntry.getCategoryKey().getStringId()
                    );
                    return new AuditEntryLookupResult.Data(auditEntry, auditCategory, propertyDetailMap);
                })
                .collect(Collectors.toList());
        return new AuditEntryLookupResult(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                data
        );
    }
}
