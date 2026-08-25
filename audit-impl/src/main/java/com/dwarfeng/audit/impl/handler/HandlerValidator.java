package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.audit.sdk.util.Constants;
import com.dwarfeng.audit.stack.bean.dto.AuditRecordInfo;
import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.audit.stack.exception.*;
import com.dwarfeng.audit.stack.handler.AuditRecordLocalCacheHandler;
import com.dwarfeng.audit.stack.struct.AuditRecordLocalCache;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

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

    public HandlerValidator(AuditRecordLocalCacheHandler auditRecordLocalCacheHandler) {
        this.auditRecordLocalCacheHandler = auditRecordLocalCacheHandler;
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
}
