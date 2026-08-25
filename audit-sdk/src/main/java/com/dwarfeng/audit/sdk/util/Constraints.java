package com.dwarfeng.audit.sdk.util;

/**
 * 约束类。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public final class Constraints {

    /**
     * 属性 ID 的长度约束。
     */
    public static final int LENGTH_PROPERTY_ID = 100;

    /**
     * 名称的长度约束。
     */
    public static final int LENGTH_NAME = 50;

    /**
     * 标签的长度约束。
     */
    public static final int LENGTH_LABEL = 50;

    /**
     * 备注的长度约束。
     */
    public static final int LENGTH_REMARK = 200;

    /**
     * 类型的长度约束。
     */
    public static final int LENGTH_TYPE = 50;

    private Constraints() {
        throw new IllegalStateException("禁止实例化");
    }
}
