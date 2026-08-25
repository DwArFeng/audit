package com.dwarfeng.audit.sdk.util;

import com.dwarfeng.subgrade.stack.exception.ServiceException;

/**
 * 服务异常代码。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public final class ServiceExceptionCodes {

    private static int EXCEPTION_CODE_OFFSET = 1000;

    public static final ServiceException.Code AUDIT_CATEGORY_DISABLED =
            new ServiceException.Code(offset(0), "audit category disabled");
    public static final ServiceException.Code AUDIT_CATEGORY_NOT_EXISTS =
            new ServiceException.Code(offset(10), "audit category not exists");
    public static final ServiceException.Code AUDIT_PROPERTY_INDICATOR_NOT_EXISTS =
            new ServiceException.Code(offset(20), "audit property indicator not exists");
    public static final ServiceException.Code AUDIT_PROPERTY_VALUE_TYPE_MISMATCH =
            new ServiceException.Code(offset(30), "audit property value type mismatch");
    public static final ServiceException.Code AUDIT_RECORD_HANDLER_STOPPED =
            new ServiceException.Code(offset(40), "audit record handler stopped");
    public static final ServiceException.Code CONSUME_STOPPED =
            new ServiceException.Code(offset(50), "consume stopped");
    public static final ServiceException.Code INVALID_AUDIT_PROPERTY_TYPE =
            new ServiceException.Code(offset(60), "invalid audit property type");
    public static final ServiceException.Code INVALID_AUDIT_RECORD_INFO =
            new ServiceException.Code(offset(70), "invalid audit record info");
    public static final ServiceException.Code INVALID_AUDIT_ENTRY_COMPOSITE_LOOKUP_INFO =
            new ServiceException.Code(offset(80), "invalid audit entry composite lookup info");
    public static final ServiceException.Code INVALID_AUDIT_ENTRY_GROUPED_LOOKUP_INFO =
            new ServiceException.Code(offset(90), "invalid audit entry grouped lookup info");

    private static int offset(int value) {
        return EXCEPTION_CODE_OFFSET + value;
    }

    /**
     * 获取异常代号的偏移量。
     *
     * @return 异常代号的偏移量。
     */
    public static int getExceptionCodeOffset() {
        return EXCEPTION_CODE_OFFSET;
    }

    /**
     * 设置异常代号的偏移量。
     *
     * @param exceptionCodeOffset 指定的异常代号的偏移量。
     */
    public static void setExceptionCodeOffset(int exceptionCodeOffset) {
        // 设置 EXCEPTION_CODE_OFFSET 的值。
        EXCEPTION_CODE_OFFSET = exceptionCodeOffset;

        // 以新的 EXCEPTION_CODE_OFFSET 为基准，更新异常代码的值。
        AUDIT_CATEGORY_DISABLED.setCode(offset(0));
        AUDIT_CATEGORY_NOT_EXISTS.setCode(offset(10));
        AUDIT_PROPERTY_INDICATOR_NOT_EXISTS.setCode(offset(20));
        AUDIT_PROPERTY_VALUE_TYPE_MISMATCH.setCode(offset(30));
        AUDIT_RECORD_HANDLER_STOPPED.setCode(offset(40));
        CONSUME_STOPPED.setCode(offset(50));
        INVALID_AUDIT_PROPERTY_TYPE.setCode(offset(60));
        INVALID_AUDIT_RECORD_INFO.setCode(offset(70));
        INVALID_AUDIT_ENTRY_COMPOSITE_LOOKUP_INFO.setCode(offset(80));
        INVALID_AUDIT_ENTRY_GROUPED_LOOKUP_INFO.setCode(offset(90));
    }

    private ServiceExceptionCodes() {
        throw new IllegalStateException("禁止实例化");
    }
}
