package com.dwarfeng.audit.sdk.util;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 逻辑运算符字段有效性验证注解。
 *
 * @author DwArFeng
 * @since 1.2.0
 */
@Documented
@Constraint(validatedBy = ValidLogicOperator.InternalConstraintValidator.class)
@Target({
        ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLogicOperator {

    String message() default "invalid logic operator";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class InternalConstraintValidator implements ConstraintValidator<ValidLogicOperator, Integer> {

        @Override
        public boolean isValid(Integer value, ConstraintValidatorContext context) {
            return value != null && Constants.logicOperatorSpace().contains(value);
        }
    }
}
