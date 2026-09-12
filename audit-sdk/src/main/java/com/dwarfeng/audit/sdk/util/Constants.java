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

    @InspectionTaskStatusItem
    public static final int INSPECTION_TASK_STATUS_CREATED = 0;
    @InspectionTaskStatusItem
    public static final int INSPECTION_TASK_STATUS_PROCESSING = 1;
    @InspectionTaskStatusItem
    public static final int INSPECTION_TASK_STATUS_FINISHED = 2;
    @InspectionTaskStatusItem
    public static final int INSPECTION_TASK_STATUS_FAILED = 3;
    @InspectionTaskStatusItem
    public static final int INSPECTION_TASK_STATUS_EXPIRED = 4;
    @InspectionTaskStatusItem
    public static final int INSPECTION_TASK_STATUS_DIED = 5;

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

    @InspectorVariableValueTypeItem
    public static final int INSPECTOR_VARIABLE_VALUE_TYPE_STRING = 0;
    @InspectorVariableValueTypeItem
    public static final int INSPECTOR_VARIABLE_VALUE_TYPE_LONG = 1;
    @InspectorVariableValueTypeItem
    public static final int INSPECTOR_VARIABLE_VALUE_TYPE_DOUBLE = 2;
    @InspectorVariableValueTypeItem
    public static final int INSPECTOR_VARIABLE_VALUE_TYPE_BOOLEAN = 3;
    @InspectorVariableValueTypeItem
    public static final int INSPECTOR_VARIABLE_VALUE_TYPE_DATE = 4;

    @LogicOperatorItem
    public static final int LOGIC_OPERATOR_AND = 0;
    @LogicOperatorItem
    public static final int LOGIC_OPERATOR_OR = 1;

    /**
     * 检查任务的执行间隔。
     */
    public static final long SCHEDULER_CHECK_INTERVAL = 5000L;

    private static final Lock LOCK = new ReentrantLock();

    private static List<Integer> inspectionTaskStatusSpace;
    private static List<Integer> auditPropertyTypeSpace;
    private static List<Integer> inspectorVariableValueTypeSpace;
    private static List<Integer> logicOperatorSpace;

    /**
     * 自动审计任务状态空间。
     *
     * @return 自动审计任务状态空间。
     */
    public static List<Integer> inspectionTaskStatusSpace() {
        if (Objects.nonNull(inspectionTaskStatusSpace)) {
            return inspectionTaskStatusSpace;
        }
        LOCK.lock();
        try {
            if (Objects.nonNull(inspectionTaskStatusSpace)) {
                return inspectionTaskStatusSpace;
            }
            List<Integer> result = new ArrayList<>();
            for (Field declaredField : Constants.class.getDeclaredFields()) {
                if (!declaredField.isAnnotationPresent(InspectionTaskStatusItem.class)) {
                    continue;
                }
                try {
                    result.add((Integer) declaredField.get(null));
                } catch (Exception e) {
                    throw new IllegalStateException("初始化自动审计任务状态空间失败", e);
                }
            }
            inspectionTaskStatusSpace = Collections.unmodifiableList(result);
            return inspectionTaskStatusSpace;
        } finally {
            LOCK.unlock();
        }
    }

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

    /**
     * 审计器变量值类型空间。
     *
     * @return 审计器变量值类型空间。
     */
    public static List<Integer> inspectorVariableValueTypeSpace() {
        if (Objects.nonNull(inspectorVariableValueTypeSpace)) {
            return inspectorVariableValueTypeSpace;
        }
        LOCK.lock();
        try {
            if (Objects.nonNull(inspectorVariableValueTypeSpace)) {
                return inspectorVariableValueTypeSpace;
            }
            List<Integer> result = new ArrayList<>();
            for (Field declaredField : Constants.class.getDeclaredFields()) {
                if (!declaredField.isAnnotationPresent(InspectorVariableValueTypeItem.class)) {
                    continue;
                }
                try {
                    result.add((Integer) declaredField.get(null));
                } catch (Exception e) {
                    throw new IllegalStateException("初始化审计器变量值类型空间失败", e);
                }
            }
            inspectorVariableValueTypeSpace = Collections.unmodifiableList(result);
            return inspectorVariableValueTypeSpace;
        } finally {
            LOCK.unlock();
        }
    }

    /**
     * 逻辑运算符空间。
     *
     * @return 逻辑运算符空间。
     */
    public static List<Integer> logicOperatorSpace() {
        if (Objects.nonNull(logicOperatorSpace)) {
            return logicOperatorSpace;
        }
        LOCK.lock();
        try {
            if (Objects.nonNull(logicOperatorSpace)) {
                return logicOperatorSpace;
            }
            List<Integer> result = new ArrayList<>();
            for (Field declaredField : Constants.class.getDeclaredFields()) {
                if (!declaredField.isAnnotationPresent(LogicOperatorItem.class)) {
                    continue;
                }
                try {
                    result.add((Integer) declaredField.get(null));
                } catch (Exception e) {
                    throw new IllegalStateException("初始化逻辑运算符空间失败", e);
                }
            }
            logicOperatorSpace = Collections.unmodifiableList(result);
            return logicOperatorSpace;
        } finally {
            LOCK.unlock();
        }
    }

    private Constants() {
        throw new IllegalStateException("禁止实例化");
    }
}
