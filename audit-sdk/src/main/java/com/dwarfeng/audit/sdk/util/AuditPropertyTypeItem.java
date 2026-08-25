package com.dwarfeng.audit.sdk.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 审计属性类型常量标记注解。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditPropertyTypeItem {
}
