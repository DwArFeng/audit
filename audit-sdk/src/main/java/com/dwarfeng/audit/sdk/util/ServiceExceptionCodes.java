package com.dwarfeng.audit.sdk.util;

/**
 * 服务异常代码。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public final class ServiceExceptionCodes {

    private static int EXCEPTION_CODE_OFFSET = 1000;

    @SuppressWarnings("unused")
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
        EXCEPTION_CODE_OFFSET = exceptionCodeOffset;
    }

    private ServiceExceptionCodes() {
        throw new IllegalStateException("禁止实例化");
    }
}
