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
        map.put(
                InvalidAuditEntryCompositeLookupInfoException.class,
                ServiceExceptionCodes.INVALID_AUDIT_ENTRY_COMPOSITE_LOOKUP_INFO
        );
        map.put(
                InvalidAuditEntryGroupedLookupInfoException.class,
                ServiceExceptionCodes.INVALID_AUDIT_ENTRY_GROUPED_LOOKUP_INFO
        );
        map.put(InspectionNotExistsException.class, ServiceExceptionCodes.INSPECTION_NOT_EXISTS);
        map.put(InspectionTaskNotExistsException.class, ServiceExceptionCodes.INSPECTION_TASK_NOT_EXISTS);
        map.put(InspectorInfoNotExistsException.class, ServiceExceptionCodes.INSPECTOR_INFO_NOT_EXISTS);
        map.put(
                InspectionTaskInspectionMismatchException.class,
                ServiceExceptionCodes.INSPECTION_TASK_INSPECTION_MISMATCH
        );
        map.put(
                InspectorInfoInspectionMismatchException.class,
                ServiceExceptionCodes.INSPECTOR_INFO_INSPECTION_MISMATCH
        );
        map.put(InspectorVariableNotExistsException.class, ServiceExceptionCodes.INSPECTOR_VARIABLE_NOT_EXISTS);
        map.put(InvalidVariableValueTypeException.class, ServiceExceptionCodes.INVALID_VARIABLE_VALUE_TYPE);
        map.put(VariableValueTypeMismatchException.class, ServiceExceptionCodes.VARIABLE_VALUE_TYPE_MISMATCH);
        map.put(InspectorException.class, ServiceExceptionCodes.INSPECTOR_FAILED);
        map.put(InspectorMakeException.class, ServiceExceptionCodes.INSPECTOR_MAKE_FAILED);
        map.put(InspectorExecutionException.class, ServiceExceptionCodes.INSPECTOR_EXECUTION_FAILED);
        map.put(UnsupportedInspectorTypeException.class, ServiceExceptionCodes.INSPECTOR_TYPE_UNSUPPORTED);
        map.put(InspectionReceiverException.class, ServiceExceptionCodes.INSPECTION_RECEIVER_FAILED);
        map.put(InspectionReceiverNotStartException.class, ServiceExceptionCodes.INSPECTION_RECEIVER_NOT_START);
        map.put(
                InspectionReceiverExecutionException.class,
                ServiceExceptionCodes.INSPECTION_RECEIVER_EXECUTION_FAILED
        );
        map.put(
                InspectionDispatcherException.class,
                ServiceExceptionCodes.INSPECTION_DISPATCHER_FAILED
        );
        map.put(
                InspectionDispatcherNotStartException.class,
                ServiceExceptionCodes.INSPECTION_DISPATCHER_NOT_START
        );
        map.put(
                InspectionDispatcherExecutionException.class,
                ServiceExceptionCodes.INSPECTION_DISPATCHER_EXECUTION_FAILED
        );
        map.put(InspectionDriverException.class, ServiceExceptionCodes.INSPECTION_DRIVER_FAILED);
        map.put(
                UnsupportedInspectionDriverTypeException.class,
                ServiceExceptionCodes.INSPECTION_DRIVER_TYPE_UNSUPPORTED
        );

        return map;
    }

    private ServiceExceptionHelper() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
