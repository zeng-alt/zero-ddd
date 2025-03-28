package com.zjj.core.component.type;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月27日 17:26
 */
public class TypeConversionHelper implements BeanFactoryPostProcessor {

    private static JsonConversionContext jsonConversionContext;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.jsonConversionContext = beanFactory.getBean(JsonConversionContext.class);
    }

    public static <T> T convert(String type, String json) {
        return jsonConversionContext.convert(type, json);
    }


    public static Set<String> getTypes() {
        return jsonConversionContext.getTypes();
    }

    public static JsonConversionStrategy getStrategy(String type) {
        return jsonConversionContext.getStrategy(type);
    }
}
