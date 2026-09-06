package com.dwarfeng.audit.stack.exception;

/**
 * 不支持的审计器类型异常。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class UnsupportedInspectorTypeException extends InspectorException {

    private static final long serialVersionUID = -6741515465750207312L;

    private final String type;

    public UnsupportedInspectorTypeException(String type) {
        this.type = type;
    }

    public UnsupportedInspectorTypeException(Throwable cause, String type) {
        super(cause);
        this.type = type;
    }

    @Override
    public String getMessage() {
        return "不支持的审计器类型: " + type;
    }
}
