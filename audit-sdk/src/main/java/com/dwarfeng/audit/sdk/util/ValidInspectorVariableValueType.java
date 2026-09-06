package com.dwarfeng.audit.sdk.util;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 审计器变量值类型字段有效性验证注解。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Documented
@Constraint(validatedBy = ValidInspectorVariableValueType.InternalConstraintValidator.class)
@Target({
        ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidInspectorVariableValueType {

    String message() default "invalid inspector variable value type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class InternalConstraintValidator implements ConstraintValidator<ValidInspectorVariableValueType, Integer> {

        @Override
        public boolean isValid(Integer value, ConstraintValidatorContext context) {
            try {
                return Constants.inspectorVariableValueTypeSpace().contains(value);
            } catch (Exception e) {
                return false;
            }
        }
    }
}
