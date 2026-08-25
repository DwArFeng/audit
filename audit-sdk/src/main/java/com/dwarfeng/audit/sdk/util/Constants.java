package com.dwarfeng.audit.sdk.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 常量类。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public final class Constants {

    @AuditPropertyTypeItem
    public static final int PROPERTY_TYPE_STRING = 0;
    @AuditPropertyTypeItem
    public static final int PROPERTY_TYPE_LONG = 1;
    @AuditPropertyTypeItem
    public static final int PROPERTY_TYPE_DOUBLE = 2;
    @AuditPropertyTypeItem
    public static final int PROPERTY_TYPE_BOOLEAN = 3;
    @AuditPropertyTypeItem
    public static final int PROPERTY_TYPE_DATE = 4;

    private static final Lock LOCK = new ReentrantLock();

    private static List<Integer> auditPropertyTypeSpace;

    /**
     * 属性类型空间。
     *
     * @return 属性类型空间。
     */
    public static List<Integer> auditPropertyTypeSpace() {
        if (Objects.nonNull(auditPropertyTypeSpace)) {
            return auditPropertyTypeSpace;
        }
        LOCK.lock();
        try {
            if (Objects.nonNull(auditPropertyTypeSpace)) {
                return auditPropertyTypeSpace;
            }
            List<Integer> result = new ArrayList<>();
            for (Field declaredField : Constants.class.getDeclaredFields()) {
                if (!declaredField.isAnnotationPresent(AuditPropertyTypeItem.class)) {
                    continue;
                }
                try {
                    result.add((Integer) declaredField.get(null));
                } catch (Exception e) {
                    throw new IllegalStateException("初始化属性类型空间失败", e);
                }
            }
            auditPropertyTypeSpace = Collections.unmodifiableList(result);
            return auditPropertyTypeSpace;
        } finally {
            LOCK.unlock();
        }
    }

    private Constants() {
        throw new IllegalStateException("禁止实例化");
    }
}
