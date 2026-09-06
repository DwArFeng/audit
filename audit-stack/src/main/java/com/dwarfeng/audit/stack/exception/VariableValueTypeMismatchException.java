package com.dwarfeng.audit.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 变量值类型不匹配异常。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class VariableValueTypeMismatchException extends HandlerException {

    private static final long serialVersionUID = -8399446338952500219L;

    private final int valueType;
    private final Class<?> expectedValueClazz;
    private final Class<?> actualValueClazz;

    public VariableValueTypeMismatchException(int valueType, Class<?> expectedValueClazz, Class<?> actualValueClazz) {
        this.valueType = valueType;
        this.expectedValueClazz = expectedValueClazz;
        this.actualValueClazz = actualValueClazz;
    }

    public VariableValueTypeMismatchException(
            Throwable cause, int valueType, Class<?> expectedValueClazz, Class<?> actualValueClazz
    ) {
        super(cause);
        this.valueType = valueType;
        this.expectedValueClazz = expectedValueClazz;
        this.actualValueClazz = actualValueClazz;
    }

    @Override
    public String getMessage() {
        return "变量值类型不匹配, 变量值类型为 " + valueType + ", 期望的值类型为 " + expectedValueClazz +
                ", 实际的值类型为 " + actualValueClazz;
    }
}
