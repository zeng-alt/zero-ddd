package com.zjj.excel.component.utils;

import cn.idev.excel.context.AnalysisContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月25日 18:52
 */
public class ValidaHelper implements BeanFactoryPostProcessor {

    private static Validator validator;


    public static <T> void validate(T object, AnalysisContext context, Class<?>... groups) {
        Set<ConstraintViolation<T>> validate = validator.validate(object, groups);
        if (validate.isEmpty()) {
            return;
        }
        ToStringBuilder builder = new ToStringBuilder(object, ToStringStyle.MULTI_LINE_STYLE);

        for (ConstraintViolation<T> constraintViolation : validate) {
            builder.append(constraintViolation.getMessage());
        }


        String message = builder.toString();
        if (context == null) {
            throw new ValidationException(message);
        }

        throw new ValidationException("row->" + context.readRowHolder().getRowIndex() + ": " + message);
    }

    public static <T> void validate(T object, Class<?>... groups) {
        validate(object, null, groups);
    }


    public static  <T> Set<ConstraintViolation<T>> validateProperty(T object,
                                                     String propertyName,
                                                     Class<?>... groups) {
        return validator.validateProperty(object, propertyName, groups);
    }


    public static  <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType,
                                                  String propertyName,
                                                  Object value,
                                                  Class<?>... groups) {
        return validator.validateValue(beanType, propertyName, value, groups);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.validator = beanFactory.getBean(Validator.class);
    }
}
