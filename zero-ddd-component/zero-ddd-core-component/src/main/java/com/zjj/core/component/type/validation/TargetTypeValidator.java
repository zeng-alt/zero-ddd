package com.zjj.core.component.type.validation;

import com.zjj.core.component.type.TypeConversionHelper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月28日 21:56
 */
public class TargetTypeValidator implements ConstraintValidator<TargetTypeValidation, String> {

    private String message;

    @Autowired
    private MessageSourceAccessor messageSource;

    @Override
    public void initialize(TargetTypeValidation constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (TypeConversionHelper.getTypes().contains(value)) {
            String dynamicMessage = messageSource.getMessage(message, new Object[]{value}, message);
            // 动态构造国际化消息
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(dynamicMessage)
                    .addConstraintViolation();
            return true;
        }

        return false;
    }
}
