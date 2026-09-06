package com.dwarfeng.audit.sdk.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 审计器变量值类型条目。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InspectorVariableValueTypeItem {
}
