package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.sdk.util.Constants;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryCompositeLookupInfo;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryGroupedLookupInfo;
import com.dwarfeng.audit.stack.bean.dto.AuditRecordInfo;
import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.audit.stack.bean.entity.AuditPropertyIndicator;
import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.audit.stack.bean.entity.InspectorInfo;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.audit.stack.bean.key.InspectorVariableKey;
import com.dwarfeng.audit.stack.exception.*;
import com.dwarfeng.audit.stack.handler.AuditRecordLocalCacheHandler;
import com.dwarfeng.audit.stack.service.InspectionMaintainService;
import com.dwarfeng.audit.stack.service.InspectionTaskMaintainService;
import com.dwarfeng.audit.stack.service.InspectorInfoMaintainService;
import com.dwarfeng.audit.stack.service.InspectorVariableMaintainService;
import com.dwarfeng.audit.stack.struct.AuditRecordLocalCache;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 处理器验证器。
 *
 * <p>
 * 为处理器提供公共的验证方法。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
@Component
public class HandlerValidator {

    private final AuditRecordLocalCacheHandler auditRecordLocalCacheHandler;
    private final InspectionMaintainService inspectionMaintainService;
    private final InspectionTaskMaintainService inspectionTaskMaintainService;
    private final InspectorInfoMaintainService inspectorInfoMaintainService;
    private final InspectorVariableMaintainService inspectorVariableMaintainService;

    public HandlerValidator(
            AuditRecordLocalCacheHandler auditRecordLocalCacheHandler,
            InspectionMaintainService inspectionMaintainService,
            InspectionTaskMaintainService inspectionTaskMaintainService,
            InspectorInfoMaintainService inspectorInfoMaintainService,
            InspectorVariableMaintainService inspectorVariableMaintainService
    ) {
        this.auditRecordLocalCacheHandler = auditRecordLocalCacheHandler;
        this.inspectionMaintainService = inspectionMaintainService;
        this.inspectionTaskMaintainService = inspectionTaskMaintainService;
        this.inspectorInfoMaintainService = inspectorInfoMaintainService;
        this.inspectorVariableMaintainService = inspectorVariableMaintainService;
    }

    public void makeSureAuditCategoryExists(StringIdKey categoryKey) throws HandlerException {
        if (Objects.isNull(categoryKey) || Objects.isNull(auditRecordLocalCacheHandler.get(categoryKey))) {
            throw new AuditCategoryNotExistsException(categoryKey);
        }
    }

    public void makeSureAuditCategoryEnabled(StringIdKey categoryKey) throws HandlerException {
        AuditRecordLocalCache localCache = auditRecordLocalCacheHandler.get(categoryKey);
        if (Objects.isNull(localCache)) {
            throw new AuditCategoryNotExistsException(categoryKey);
        }
        AuditCategory category = localCache.getAuditCategory();
        if (!category.isEnabled()) {
            throw new AuditCategoryDisabledException(categoryKey);
        }
    }

    public void makeSureAuditPropertyIndicatorExists(AuditPropertyIndicatorKey indicatorKey) throws HandlerException {
        if (Objects.isNull(indicatorKey) || Objects.isNull(indicatorKey.getAuditCategoryStringId())) {
            throw new AuditPropertyIndicatorNotExistsException(indicatorKey);
        }
        AuditRecordLocalCache localCache = auditRecordLocalCacheHandler.get(
                new StringIdKey(indicatorKey.getAuditCategoryStringId())
        );
        if (Objects.isNull(localCache) || !localCache.getAuditPropertyIndicatorMap().containsKey(
                indicatorKey.getPropertyStringId()
        )) {
            throw new AuditPropertyIndicatorNotExistsException(indicatorKey);
        }
    }

    @SuppressWarnings("ConstantValue")
    public void makeSureAuditRecordInfoValid(AuditRecordInfo auditRecordInfo) throws HandlerException {
        if (Objects.isNull(auditRecordInfo)) {
            throw new InvalidAuditRecordInfoException(auditRecordInfo);
        }
        StringIdKey categoryKey = auditRecordInfo.getCategoryKey();
        if (Objects.isNull(categoryKey)) {
            throw new InvalidAuditRecordInfoException(auditRecordInfo);
        }
        Map<String, Object> properties = auditRecordInfo.getProperties();
        if (Objects.isNull(properties)) {
            throw new InvalidAuditRecordInfoException(auditRecordInfo);
        }
    }

    public void makeSureAuditEntryCompositeLookupInfoValid(AuditEntryCompositeLookupInfo lookupInfo)
            throws HandlerException {
        if (Objects.isNull(lookupInfo)) {
            throw new InvalidAuditEntryCompositeLookupInfoException(null, "查询信息不能为 null");
        }
        if (Objects.isNull(lookupInfo.getPagingInfo())) {
            throw new InvalidAuditEntryCompositeLookupInfoException(lookupInfo, "分页信息不能为 null");
        }
        makeSureLookupItemValid(
                lookupInfo, lookupInfo.getCategoryKey(), lookupInfo.getStartCreatedDate(),
                lookupInfo.getEndCreatedDate()
        );
        List<AuditEntryCompositeLookupInfo.PropertyCondition> propertyConditions =
                lookupInfo.getPropertyConditions();
        if (Objects.isNull(propertyConditions)) {
            return;
        }
        for (AuditEntryCompositeLookupInfo.PropertyCondition propertyCondition : propertyConditions) {
            if (Objects.isNull(propertyCondition)) {
                throw new InvalidAuditEntryCompositeLookupInfoException(lookupInfo, "属性条件不能为 null");
            }
            if (!propertyCondition.isEnabled()) {
                continue;
            }
            makeSurePropertyConditionValid(
                    lookupInfo, lookupInfo.getCategoryKey(), propertyCondition.getPropertyId(),
                    propertyCondition.getPropertyType(), propertyCondition.getPropertyValue()
            );
        }
    }

    public void makeSureAuditEntryGroupedLookupInfoValid(AuditEntryGroupedLookupInfo lookupInfo)
            throws HandlerException {
        if (Objects.isNull(lookupInfo)) {
            throw new InvalidAuditEntryGroupedLookupInfoException(null, "查询信息不能为 null");
        }
        if (Objects.isNull(lookupInfo.getPagingInfo())) {
            throw new InvalidAuditEntryGroupedLookupInfoException(lookupInfo, "分页信息不能为 null");
        }
        Set<AuditEntryGroupedLookupInfo.QueryGroup> visiting =
                Collections.newSetFromMap(new IdentityHashMap<>());
        makeSureQueryGroupValid(
                lookupInfo, lookupInfo.getLogicOperator(), lookupInfo.getLookupItems(), lookupInfo.getQueryGroups(),
                visiting
        );
    }

    private void makeSureQueryGroupValid(
            AuditEntryGroupedLookupInfo lookupInfo,
            int logicOperator,
            List<AuditEntryGroupedLookupInfo.LookupItem> lookupItems,
            List<AuditEntryGroupedLookupInfo.QueryGroup> queryGroups,
            Set<AuditEntryGroupedLookupInfo.QueryGroup> visiting
    ) throws HandlerException {
        makeSureLogicOperatorValid(lookupInfo, logicOperator);

        if (Objects.nonNull(lookupItems)) {
            for (AuditEntryGroupedLookupInfo.LookupItem lookupItem : lookupItems) {
                if (Objects.isNull(lookupItem)) {
                    throw new InvalidAuditEntryGroupedLookupInfoException(lookupInfo, "查询项不能为 null");
                }
                if (!lookupItem.isEnabled()) {
                    continue;
                }
                makeSureGroupedLookupItemValid(lookupInfo, lookupItem);
            }
        }

        if (Objects.nonNull(queryGroups)) {
            for (AuditEntryGroupedLookupInfo.QueryGroup queryGroup : queryGroups) {
                if (Objects.isNull(queryGroup)) {
                    throw new InvalidAuditEntryGroupedLookupInfoException(lookupInfo, "查询组不能为 null");
                }
                if (!queryGroup.isEnabled()) {
                    continue;
                }
                if (!visiting.add(queryGroup)) {
                    throw new InvalidAuditEntryGroupedLookupInfoException(lookupInfo, "查询组不能循环引用");
                }
                try {
                    makeSureQueryGroupValid(
                            lookupInfo, queryGroup.getLogicOperator(), queryGroup.getLookupItems(),
                            queryGroup.getQueryGroups(), visiting
                    );
                } finally {
                    visiting.remove(queryGroup);
                }
            }
        }
    }

    private void makeSureGroupedLookupItemValid(
            AuditEntryGroupedLookupInfo lookupInfo, AuditEntryGroupedLookupInfo.LookupItem lookupItem
    ) throws HandlerException {
        makeSureLookupItemValid(
                lookupInfo, lookupItem.getCategoryKey(), lookupItem.getStartCreatedDate(),
                lookupItem.getEndCreatedDate()
        );
        List<AuditEntryGroupedLookupInfo.PropertyCondition> propertyConditions = lookupItem.getPropertyConditions();
        if (Objects.isNull(propertyConditions)) {
            return;
        }
        for (AuditEntryGroupedLookupInfo.PropertyCondition propertyCondition : propertyConditions) {
            if (Objects.isNull(propertyCondition)) {
                throw new InvalidAuditEntryGroupedLookupInfoException(lookupInfo, "属性条件不能为 null");
            }
            if (!propertyCondition.isEnabled()) {
                continue;
            }
            makeSurePropertyConditionValid(
                    lookupInfo, lookupItem.getCategoryKey(), propertyCondition.getPropertyId(),
                    propertyCondition.getPropertyType(), propertyCondition.getPropertyValue()
            );
        }
    }

    private void makeSureLookupItemValid(
            AuditEntryCompositeLookupInfo lookupInfo, StringIdKey categoryKey,
            Date startCreatedDate, Date endCreatedDate
    ) throws HandlerException {
        String invalidDetail = getLookupItemInvalidDetail(categoryKey, startCreatedDate, endCreatedDate);
        if (Objects.nonNull(invalidDetail)) {
            throw new InvalidAuditEntryCompositeLookupInfoException(lookupInfo, invalidDetail);
        }
    }

    private void makeSureLookupItemValid(
            AuditEntryGroupedLookupInfo lookupInfo, StringIdKey categoryKey,
            Date startCreatedDate, Date endCreatedDate
    ) throws HandlerException {
        String invalidDetail = getLookupItemInvalidDetail(categoryKey, startCreatedDate, endCreatedDate);
        if (Objects.nonNull(invalidDetail)) {
            throw new InvalidAuditEntryGroupedLookupInfoException(lookupInfo, invalidDetail);
        }
    }

    private void makeSurePropertyConditionValid(
            AuditEntryCompositeLookupInfo lookupInfo, StringIdKey categoryKey,
            String propertyId, int propertyType, Object propertyValue
    ) throws HandlerException {
        String invalidDetail = getPropertyConditionInvalidDetail(
                categoryKey, propertyId, propertyType, propertyValue
        );
        if (Objects.nonNull(invalidDetail)) {
            throw new InvalidAuditEntryCompositeLookupInfoException(lookupInfo, invalidDetail);
        }
    }

    private void makeSurePropertyConditionValid(
            AuditEntryGroupedLookupInfo lookupInfo, StringIdKey categoryKey,
            String propertyId, int propertyType, Object propertyValue
    ) throws HandlerException {
        String invalidDetail = getPropertyConditionInvalidDetail(
                categoryKey, propertyId, propertyType, propertyValue
        );
        if (Objects.nonNull(invalidDetail)) {
            throw new InvalidAuditEntryGroupedLookupInfoException(lookupInfo, invalidDetail);
        }
    }

    private String getLookupItemInvalidDetail(
            StringIdKey categoryKey, Date startCreatedDate, Date endCreatedDate
    ) throws HandlerException {
        if (Objects.isNull(categoryKey)) {
            return "审计类别主键不能为 null";
        }
        makeSureAuditCategoryExists(categoryKey);
        if (Objects.nonNull(startCreatedDate) && Objects.nonNull(endCreatedDate) &&
                startCreatedDate.after(endCreatedDate)) {
            return "创建时间起始值不能晚于结束值";
        }
        return null;
    }

    private String getPropertyConditionInvalidDetail(
            StringIdKey categoryKey, String propertyId, int propertyType, Object propertyValue
    ) throws HandlerException {
        if (Objects.isNull(propertyId) || propertyId.trim().isEmpty()) {
            return "属性 ID 不能为 null 或空字符串";
        }
        if (Objects.isNull(propertyValue)) {
            return "属性值不能为 null";
        }

        AuditPropertyIndicatorKey indicatorKey = new AuditPropertyIndicatorKey(categoryKey.getStringId(), propertyId);
        makeSureAuditPropertyIndicatorExists(indicatorKey);
        AuditRecordLocalCache localCache = auditRecordLocalCacheHandler.get(categoryKey);
        AuditPropertyIndicator indicator = localCache.getAuditPropertyIndicatorMap().get(propertyId);
        if (indicator.getPropertyType() != propertyType) {
            return "属性 " + propertyId + " 的类型不匹配, 期望类型: " + indicator.getPropertyType() +
                    ", 实际类型: " + propertyType;
        }
        makeSureAuditPropertyValueValid(propertyId, propertyType, propertyValue);
        return null;
    }

    private void makeSureLogicOperatorValid(AuditEntryGroupedLookupInfo lookupInfo, int logicOperator)
            throws HandlerException {
        if (logicOperator != AuditEntryGroupedLookupInfo.LOGIC_OPERATOR_AND &&
                logicOperator != AuditEntryGroupedLookupInfo.LOGIC_OPERATOR_OR) {
            throw new InvalidAuditEntryGroupedLookupInfoException(
                    lookupInfo, "非法的逻辑连接符: " + logicOperator
            );
        }
    }

    public void makeSureAuditPropertyValueValid(String propertyId, int propertyType, Object value)
            throws HandlerException {
        if (Objects.isNull(value)) {
            return;
        }

        Class<?> expectedType;
        switch (propertyType) {
            case Constants.PROPERTY_TYPE_STRING:
                expectedType = String.class;
                break;
            case Constants.PROPERTY_TYPE_LONG:
                expectedType = Long.class;
                break;
            case Constants.PROPERTY_TYPE_DOUBLE:
                expectedType = Double.class;
                break;
            case Constants.PROPERTY_TYPE_BOOLEAN:
                expectedType = Boolean.class;
                break;
            case Constants.PROPERTY_TYPE_DATE:
                expectedType = java.util.Date.class;
                break;
            default:
                throw new InvalidAuditPropertyTypeException(propertyId, propertyType);
        }

        Class<?> actualType = value.getClass();
        if (!expectedType.isAssignableFrom(actualType)) {
            throw new AuditPropertyValueTypeMismatchException(propertyId, expectedType, actualType);
        }
    }

    public void makeSureInspectionExists(LongIdKey inspectionKey) throws HandlerException {
        try {
            if (Objects.isNull(inspectionKey) || !inspectionMaintainService.exists(inspectionKey)) {
                throw new InspectionNotExistsException(inspectionKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureInspectionTaskExists(LongIdKey inspectionTaskKey) throws HandlerException {
        try {
            if (Objects.isNull(inspectionTaskKey) || !inspectionTaskMaintainService.exists(inspectionTaskKey)) {
                throw new InspectionTaskNotExistsException(inspectionTaskKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureInspectionTaskStatusValid(LongIdKey inspectionTaskKey, Set<Integer> validStatusSet)
            throws HandlerException {
        try {
            InspectionTask inspectionTask = inspectionTaskMaintainService.getIfExists(inspectionTaskKey);
            if (Objects.isNull(inspectionTask)) {
                throw new InspectionTaskNotExistsException(inspectionTaskKey);
            }
            int status = inspectionTask.getStatus();
            if (!Constants.inspectionTaskStatusSpace().contains(status)) {
                throw new InvalidInspectionTaskStatusException(status);
            }
            if (!validStatusSet.contains(status)) {
                throw new InspectionTaskStatusMismatchException(validStatusSet, status);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureInspectorInfoExists(LongIdKey inspectorInfoKey) throws HandlerException {
        try {
            if (Objects.isNull(inspectorInfoKey) || !inspectorInfoMaintainService.exists(inspectorInfoKey)) {
                throw new InspectorInfoNotExistsException(inspectorInfoKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureInspectionTaskInspectionMatched(LongIdKey inspectionTaskKey, LongIdKey expectedInspectionKey)
            throws HandlerException {
        try {
            InspectionTask inspectionTask = inspectionTaskMaintainService.getIfExists(inspectionTaskKey);
            if (Objects.isNull(inspectionTask)) {
                throw new InspectionTaskNotExistsException(inspectionTaskKey);
            }
            LongIdKey taskInspectionKey = inspectionTask.getInspectionKey();
            if (!Objects.equals(taskInspectionKey, expectedInspectionKey)) {
                throw new InspectionTaskInspectionMismatchException(
                        inspectionTaskKey, taskInspectionKey, expectedInspectionKey
                );
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureInspectorInfoInspectionMatched(LongIdKey inspectorInfoKey, LongIdKey expectedInspectionKey)
            throws HandlerException {
        try {
            InspectorInfo inspectorInfo = inspectorInfoMaintainService.getIfExists(inspectorInfoKey);
            if (Objects.isNull(inspectorInfo)) {
                throw new InspectorInfoNotExistsException(inspectorInfoKey);
            }
            LongIdKey inspectorInspectionKey = inspectorInfo.getInspectionKey();
            if (!Objects.equals(inspectorInspectionKey, expectedInspectionKey)) {
                throw new InspectorInfoInspectionMismatchException(
                        inspectorInfoKey, inspectorInspectionKey, expectedInspectionKey
                );
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureInspectorVariableExists(InspectorVariableKey inspectorVariableKey) throws HandlerException {
        try {
            if (Objects.isNull(inspectorVariableKey) ||
                    !inspectorVariableMaintainService.exists(inspectorVariableKey)) {
                throw new InspectorVariableNotExistsException(inspectorVariableKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureVariableValueTypeValid(int valueType, Object value) throws HandlerException {
        Class<?> expectedValueClazz;
        switch (valueType) {
            case Constants.INSPECTOR_VARIABLE_VALUE_TYPE_STRING:
                expectedValueClazz = String.class;
                break;
            case Constants.INSPECTOR_VARIABLE_VALUE_TYPE_LONG:
                expectedValueClazz = Long.class;
                break;
            case Constants.INSPECTOR_VARIABLE_VALUE_TYPE_DOUBLE:
                expectedValueClazz = Double.class;
                break;
            case Constants.INSPECTOR_VARIABLE_VALUE_TYPE_BOOLEAN:
                expectedValueClazz = Boolean.class;
                break;
            case Constants.INSPECTOR_VARIABLE_VALUE_TYPE_DATE:
                expectedValueClazz = Date.class;
                break;
            default:
                throw new InvalidVariableValueTypeException(valueType);
        }

        if (Objects.isNull(value)) {
            return;
        }

        Class<?> actualValueClazz = value.getClass();
        if (!expectedValueClazz.isAssignableFrom(actualValueClazz)) {
            throw new VariableValueTypeMismatchException(valueType, expectedValueClazz, actualValueClazz);
        }
    }
}
