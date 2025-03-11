package com.zjj.excel.component.dynamic;

import jakarta.validation.ConstraintValidator;
import lombok.Data;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月11日 21:23
 */
public interface DynamicAttributeService {

    public <T extends ConstraintValidator<?, ?>> DynamicAttribute<T> getDynamicAttribute(DynamicValidatorManager.CacheKey cacheKey);


    @Data
    public static class DynamicAttribute<T extends ConstraintValidator<?, ?>>  {
        private Class<?> annotationType;
        private String message;
        private List<String> argument;
        private Class<T> validator;
    }
}
