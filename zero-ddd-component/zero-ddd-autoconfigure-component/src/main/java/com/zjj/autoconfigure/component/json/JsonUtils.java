package com.zjj.autoconfigure.component.json;

import com.zjj.autoconfigure.component.UtilException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月20日 14:31
 */
@Slf4j
@Component
public class JsonUtils implements BeanFactoryPostProcessor {

    private static JsonHelper jsonHelper;


    public static String toJsonString(Object object) {
        return jsonHelper.toJsonString(object);
    }

    public static Map<String, Object> toMap(Object object) throws UtilException {
        return jsonHelper.toMap(object);
    }

    public static <T> T parseObject(String text, Class<T> clazz) throws UtilException {
        return jsonHelper.parseObject(text, clazz);
    }

    public static <T> T parseObject(byte[] bytes, Class<T> clazz) throws UtilException {
        return jsonHelper.parseObject(bytes, clazz);
    }

    public static <T> List<T> parseArray(String text, Class<T> clazz) throws UtilException {
        return jsonHelper.parseArray(text, clazz);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ObjectProvider<JsonHelper> beanProvider = beanFactory.getBeanProvider(JsonHelper.class);
        if (!beanProvider.iterator().hasNext()) {
            log.error("没有相关JsonHelper实现bean");
        }
        jsonHelper = beanProvider.getIfAvailable();
    }
}
