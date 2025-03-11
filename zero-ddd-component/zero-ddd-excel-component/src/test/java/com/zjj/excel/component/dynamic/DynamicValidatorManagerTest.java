package com.zjj.excel.component.dynamic;

import com.google.common.collect.Lists;
import com.zjj.excel.component.dynamic.constraints.SizeImpl;
import com.zjj.excel.component.i18n.I18nData;
import jakarta.annotation.Resource;
import jakarta.validation.*;
import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForCharSequence;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.valuecontext.ValueContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月11日 17:43
 */
@SpringBootTest
public class DynamicValidatorManagerTest {

    @Resource
    private LocalValidatorFactoryBean localValidatorFactoryBean;
    @Resource
    private Validator validator;
    private DynamicValidatorManager dynamicValidatorManager;
    private DynamicAttributeService dynamicAttributeService;

    @BeforeEach
    public void before() {
        dynamicAttributeService = new DynamicAttributeService() {
            @Override
            public <T extends ConstraintValidator<?, ?>> DynamicAttribute<T> getDynamicAttribute(DynamicValidatorManager.CacheKey cacheKey) {
                DynamicAttribute dynamicAttribute = new DynamicAttribute();
                dynamicAttribute.setValidator(SizeValidatorForCharSequence.class);
                dynamicAttribute.setAnnotationType(SizeImpl.class);
                dynamicAttribute.setArgument(Lists.newArrayList("2", "10"));
                dynamicAttribute.setMessage("长度在2-10之间");
                return dynamicAttribute;
            }
        };
        dynamicValidatorManager = new DynamicValidatorManagerImpl(localValidatorFactoryBean.getConstraintValidatorFactory(), dynamicAttributeService);
    }



    @Test
    public void testGetInitializedValidator() throws InvocationTargetException, IllegalAccessException {
        I18nData i18nData = new I18nData();
        i18nData.setString("1234");
        validator.validateValue(I18nData.class, "string", i18nData.getString());
//        Set<ConstraintViolation<I18nData>> validate = validator.validate(i18nData);
        DynamicConstraintValidator initializedValidator = dynamicValidatorManager.getInitializedValidator(11234L, "123445");
//        Object invoke = BeanUtils.findMethod(initializedValidator.getClass(), "isValid", String.class, ConstraintValidatorContext.class).invoke("1234", null);
//        System.out.println(invoke);
        boolean b = initializedValidator.isValid(123456);
        System.out.println(b);
    }



}
