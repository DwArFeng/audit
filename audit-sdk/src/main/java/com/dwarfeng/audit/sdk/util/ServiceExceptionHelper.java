package com.dwarfeng.audit.sdk.util;

import com.dwarfeng.audit.stack.exception.*;
import com.dwarfeng.subgrade.stack.exception.ServiceException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 异常的帮助工具类。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public final class ServiceExceptionHelper {

    /**
     * 向指定的映射中添加 audit 默认的目标映射。
     *
     * <p>
     * 该方法可以在配置类中快速的搭建目标映射。
     *
     * @param map 指定的映射，允许为 <code>null</code>。
     * @return 添加了默认目标的映射。
     */
    public static Map<Class<? extends Exception>, ServiceException.Code> putDefaultDestination(
            Map<Class<? extends Exception>, ServiceException.Code> map
    ) {
        if (Objects.isNull(map)) {
            map = new HashMap<>();
        }

        map.put(AuditCategoryDisabledException.class, ServiceExceptionCodes.AUDIT_CATEGORY_DISABLED);
        map.put(AuditCategoryNotExistsException.class, ServiceExceptionCodes.AUDIT_CATEGORY_NOT_EXISTS);
        map.put(
                AuditPropertyIndicatorNotExistsException.class,
                ServiceExceptionCodes.AUDIT_PROPERTY_INDICATOR_NOT_EXISTS
        );
        map.put(
                AuditPropertyValueTypeMismatchException.class,
                ServiceExceptionCodes.AUDIT_PROPERTY_VALUE_TYPE_MISMATCH
        );
        map.put(AuditRecordHandlerStoppedException.class, ServiceExceptionCodes.AUDIT_RECORD_HANDLER_STOPPED);
        map.put(ConsumeStoppedException.class, ServiceExceptionCodes.CONSUME_STOPPED);
        map.put(InvalidAuditPropertyTypeException.class, ServiceExceptionCodes.INVALID_AUDIT_PROPERTY_TYPE);
        map.put(InvalidAuditRecordInfoException.class, ServiceExceptionCodes.INVALID_AUDIT_RECORD_INFO);

        return map;
    }

    private ServiceExceptionHelper() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
