package com.zjj.autoconfigure.component.security.jwt.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月29日 21:13
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExpirationValidator.class)
public @interface ValidExpiration {
    String message() default "Expiration time must be greater than 20 minutes";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
