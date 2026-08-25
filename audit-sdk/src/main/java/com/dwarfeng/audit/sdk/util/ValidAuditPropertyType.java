package com.dwarfeng.audit.sdk.util;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 审计属性类型字段有效性验证注解。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
@Documented
@Constraint(validatedBy = ValidAuditPropertyType.InternalConstraintValidator.class)
@Target({
        ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAuditPropertyType {

    String message() default "invalid property type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class InternalConstraintValidator implements ConstraintValidator<ValidAuditPropertyType, Integer> {

        @Override
        public boolean isValid(Integer value, ConstraintValidatorContext context) {
            return value != null && Constants.auditPropertyTypeSpace().contains(value);
        }
    }
}
