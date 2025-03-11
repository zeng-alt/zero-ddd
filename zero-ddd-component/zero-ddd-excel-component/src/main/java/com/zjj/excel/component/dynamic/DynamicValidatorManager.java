package com.zjj.excel.component.dynamic;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorFactory;
import lombok.Data;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月11日 21:59
 */
public interface DynamicValidatorManager {

    ConstraintValidatorFactory getDefaultConstraintValidatorFactory();

    default DynamicConstraintValidator getInitializedValidator(Long rawId, String rawType) {
        return getInitializedValidator(new CacheKey(rawId, rawType));
    }

    DynamicConstraintValidator getInitializedValidator(CacheKey cacheKey);

    <A extends Annotation> ConstraintValidator<A, ?> removeValidator(CacheKey cacheKey);

    void clear();

    @Data
    final class CacheKey {
        private Long rawId;
        private String rawType;

        public CacheKey() {
        }

        public CacheKey(Long rawId, String rawType) {
            this.rawId = rawId;
            this.rawType = rawType;
        }
    }
}
