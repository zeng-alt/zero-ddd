package com.zjj.core.component.type.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月28日 10:55
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TargetTypeValidator.class)
public @interface TargetTypeValidation {
    String message() default "类型不匹配";
}
