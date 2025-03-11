package com.zjj.excel.component.dynamic;

import com.zjj.excel.component.dynamic.constraints.SizeImpl;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorFactory;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月11日 21:59
 */
public class DynamicValidatorManagerImpl implements DynamicValidatorManager {

    private static final Log LOG = LoggerFactory.make( MethodHandles.lookup() );
    private final ConstraintValidatorFactory defaultConstraintValidatorFactory;
    private final ConcurrentHashMap<CacheKey, ConstraintValidator<?, ?>> constraintValidatorCache;
    private final DynamicAttributeService dynamicAttributeService;

    /**
     * Dummy {@code ConstraintValidator} used as placeholder for the case that for a given context there exists
     * no matching constraint validator instance
     */
    private static final ConstraintValidator<?, ?> DUMMY_CONSTRAINT_VALIDATOR = (ConstraintValidator<Null, Object>) (value, context) -> false;

    public DynamicValidatorManagerImpl(ConstraintValidatorFactory defaultConstraintValidatorFactory, DynamicAttributeService dynamicAttributeService) {
        this.defaultConstraintValidatorFactory = defaultConstraintValidatorFactory;
        this.dynamicAttributeService = dynamicAttributeService;
        this.constraintValidatorCache = new ConcurrentHashMap<>();
    }

    @Override
    public ConstraintValidatorFactory getDefaultConstraintValidatorFactory() {
        return defaultConstraintValidatorFactory;
    }

    @Override
    public <A extends Annotation> ConstraintValidator<A, ?> getInitializedValidator(CacheKey cacheKey) {
        Contracts.assertNotNull(cacheKey);
        Contracts.assertNotNull(cacheKey.getRawType());
        Contracts.assertNotNull(cacheKey.getRawId());

        ConstraintValidator<A, ?> constraintValidator = (ConstraintValidator<A, ?>) constraintValidatorCache.get(cacheKey);

        if (constraintValidator == null) {
            constraintValidator = createAndInitializeValidator(cacheKey);
            constraintValidator = cacheValidator(cacheKey, constraintValidator);
        } else {
            LOG.tracef( "Constraint validator %s found in cache.", constraintValidator );
        }
        return constraintValidator;
    }

    @Override
    public <A extends Annotation> ConstraintValidator<A, ?> removeValidator(CacheKey cacheKey) {
        ConstraintValidator<A, ?> temp = (ConstraintValidator<A, ?>) this.constraintValidatorCache.remove(cacheKey);
        LOG.info("删除validator: " + cacheKey);
        return temp;
    }

    protected <A extends Annotation> ConstraintValidator<A, ?> createAndInitializeValidator(CacheKey cacheKey) {

        DynamicAttributeService.DynamicAttribute<ConstraintValidator<?, ?>> dynamicAttribute = dynamicAttributeService.getDynamicAttribute(cacheKey);
        if (dynamicAttribute == null) {
            LOG.error("根据cacheKey: " + cacheKey + "获取DynamicAttribute失败");
        }

        ConstraintValidator<A, ?> instance = (ConstraintValidator<A, ?>) this.defaultConstraintValidatorFactory.getInstance(dynamicAttribute.getValidator());
        Class<?> annotationType = dynamicAttribute.getAnnotationType();
        String message = dynamicAttribute.getMessage();
        List<String> argument = dynamicAttribute.getArgument();
        Object[] arguments = new Object[] {};
        if (!CollectionUtils.isEmpty(argument)) {
            argument = new ArrayList<>();
            argument.add(message);
        } else {
            argument.set(0, message);
        }

        arguments = argument.toArray(new String[0]);
        Constructor<?> resolvableConstructor = BeanUtils.getResolvableConstructor(annotationType);
        Object o = BeanUtils.instantiateClass(resolvableConstructor, arguments);
        instance.initialize((A) o);
        return instance;
    }


    private <A extends Annotation> ConstraintValidator<A, ?> cacheValidator(CacheKey key,
                                                                            ConstraintValidator<A, ?> constraintValidator) {

        @SuppressWarnings("unchecked")
        ConstraintValidator<A, ?> cached = (ConstraintValidator<A, ?>) constraintValidatorCache.putIfAbsent( key,
                constraintValidator != null ? constraintValidator : DUMMY_CONSTRAINT_VALIDATOR );

        return cached != null ? cached : constraintValidator;
    }

    @Override
    public void clear() {
//        for ( Map.Entry<CacheKey, ConstraintValidator<?, ?>> entry : constraintValidatorCache.entrySet() ) {
//            entry.getKey().getConstraintValidatorFactory().releaseInstance(entry.getValue());
//        }
        constraintValidatorCache.clear();
    }


}
