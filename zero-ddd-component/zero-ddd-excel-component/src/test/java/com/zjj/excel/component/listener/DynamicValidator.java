package com.zjj.excel.component.listener;

import com.zjj.excel.component.dynamic.DynamicEntity;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月01日 16:25
 */
public class DynamicValidator implements ConstraintValidator<Dynamic, List<DynamicEntity>> {

    private String message;
    private int min;
    private int max;

    @Autowired
    private MessageSourceAccessor messageSource;

    @Override
    public void initialize(Dynamic constraintAnnotation) {
        this.message = constraintAnnotation.message();
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(List<DynamicEntity> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // 可以根据需要修改为验证是否为 null
        }

        int length = value.size();
        if (length < min || length > max) {
            String dynamicMessage = messageSource.getMessage(message, new Object[]{min, max}, message);
            // 动态构造国际化消息
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(dynamicMessage)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
