package com.zjj.excel.component.dynamic;

import jakarta.validation.ConstraintValidator;
import lombok.Data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * @author zengJiaJun
 * @crateTime 2025年03月11日 22:25
 * @version 1.0
 */
@Data
public class DynamicConstraintValidator<T> {

    private String message;
    private ConstraintValidator<?, T> constraintValidator;
    private Type type;

    public static <E> DynamicConstraintValidator<E> of(ConstraintValidator<?, E> constraintValidator, String message) {
        DynamicConstraintValidator dynamicConstraintValidator = new DynamicConstraintValidator();
        dynamicConstraintValidator.setConstraintValidator(constraintValidator);
        dynamicConstraintValidator.setMessage(message);
        return dynamicConstraintValidator;
    }

    public boolean isValid(Object t) {
        return validateSingleConstraint(t, constraintValidator);
    }

    public T toValidData(String value) {
        return null;
    }

    protected final <V, A extends Annotation> boolean validateSingleConstraint(
            Object t,
            ConstraintValidator<A, V> validator) {

        return validator.isValid((V) t, null);
    }
}
