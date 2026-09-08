package com.dwarfeng.audit.stack.exception;

/**
 * 不支持的驱动器类型异常。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class UnsupportedInspectionDriverTypeException extends InspectionDriverException {

    private static final long serialVersionUID = -5752190863703599597L;

    private final String type;

    public UnsupportedInspectionDriverTypeException(String type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return "不支持的驱动器类型: " + type;
    }
}
