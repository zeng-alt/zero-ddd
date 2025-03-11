package com.zjj.excel.component.dynamic;

import com.google.common.collect.Lists;
import com.zjj.excel.component.dynamic.constraints.SizeImpl;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorFactory;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import org.apache.commons.collections4.ListUtils;
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
    private final ConcurrentHashMap<CacheKey, DynamicConstraintValidator> constraintValidatorCache;
    private final DynamicAttributeService dynamicAttributeService;

    /**
     * Dummy {@code ConstraintValidator} used as placeholder for the case that for a given context there exists
     * no matching constraint validator instance
     */
    private static final DynamicConstraintValidator DUMMY_CONSTRAINT_VALIDATOR = DynamicConstraintValidator.of((ConstraintValidator<Null, Object>) (value, context) -> false, "不能为空");

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
    public DynamicConstraintValidator getInitializedValidator(CacheKey cacheKey) {
        Contracts.assertNotNull(cacheKey);
        Contracts.assertNotNull(cacheKey.getRawType());
        Contracts.assertNotNull(cacheKey.getRawId());

        DynamicConstraintValidator constraintValidator =  constraintValidatorCache.get(cacheKey);

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

    protected <A extends Annotation> DynamicConstraintValidator createAndInitializeValidator(CacheKey cacheKey) {

        DynamicAttributeService.DynamicAttribute<ConstraintValidator<?, ?>> dynamicAttribute = dynamicAttributeService.getDynamicAttribute(cacheKey);
        if (dynamicAttribute == null) {
            LOG.error("根据cacheKey: " + cacheKey + "获取DynamicAttribute失败");
        }

        ConstraintValidator<A, ?> instance = (ConstraintValidator<A, ?>) this.defaultConstraintValidatorFactory.getInstance(dynamicAttribute.getValidator());
        Class<?> annotationType = dynamicAttribute.getAnnotationType();
        String message = dynamicAttribute.getMessage();
        List<String> argument = dynamicAttribute.getArgument();
        if (CollectionUtils.isEmpty(argument)) {
            argument = new ArrayList<>();
        }

        Constructor<?> resolvableConstructor = BeanUtils.getResolvableConstructor(annotationType);
        Class<?>[] parameterTypes = resolvableConstructor.getParameterTypes();
        for (int i = argument.size(); i < parameterTypes.length; i++) {
            argument.add(null);
        }
        Object o = BeanUtils.instantiateClass(resolvableConstructor, argument.toArray(new String[0]));
        instance.initialize((A) o);
        return DynamicConstraintValidator.of(instance, message);
    }


    private DynamicConstraintValidator cacheValidator(CacheKey key,
                                                      DynamicConstraintValidator constraintValidator) {

        @SuppressWarnings("unchecked")
        DynamicConstraintValidator cached = constraintValidatorCache.putIfAbsent( key,
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
