package com.zjj.bean.componenet;

import com.zjj.autoconfigure.component.UtilException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月27日 20:03
 */
@Component
public class ApplicationContextHelper implements ApplicationContextAware, BeanFactoryPostProcessor {

	private static ApplicationContext applicationContext;

	private static ConfigurableListableBeanFactory beanFactory;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationContextHelper.applicationContext = applicationContext;
	}

	public static <T> T getBean(Class<T> targetClz) {
		T beanInstance = null;
		// 优先按type查
		try {
			beanInstance = applicationContext.getBean(targetClz);
		}
		catch (Exception e) {
			throw new UtilException(e);
		}
		// 按name查
		if (beanInstance == null) {
			String simpleName = StringUtils.uncapitalize(targetClz.getSimpleName());
			beanInstance = applicationContext.getBean(simpleName, targetClz);
		}
		if (beanInstance == null) {
			throw new UtilException("Component " + targetClz + " can not be found in Spring Container");
		}
		return beanInstance;
	}

	public static Object getBean(String name) {
		return ApplicationContextHelper.applicationContext.getBean(name);
	}

	public static <T> T getBean(String name, Class<T> requiredType) {
		return ApplicationContextHelper.applicationContext.getBean(name, requiredType);
	}

	public static <T> T getBean(Class<T> requiredType, Object... params) {
		return ApplicationContextHelper.applicationContext.getBean(requiredType, params);
	}

	public static ConfigurableListableBeanFactory getConfigurableBeanFactory() throws UtilException {
		ConfigurableListableBeanFactory factory;
		if (null != beanFactory) {
			factory = beanFactory;
		}
		else {
			if (!(applicationContext instanceof ConfigurableApplicationContext)) {
				throw new UtilException("No ConfigurableListableBeanFactory from context!");
			}

			factory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
		}

		return factory;
	}

	public static <T> void registerBean(String beanName, T bean) {
		ConfigurableListableBeanFactory factory = getConfigurableBeanFactory();
		factory.autowireBean(bean);
		factory.registerSingleton(beanName, bean);
	}

	public static void unregisterBean(String beanName) {
		ConfigurableListableBeanFactory factory = getConfigurableBeanFactory();
		if (factory instanceof DefaultSingletonBeanRegistry registry) {
			registry.destroySingleton(beanName);
		}
		else {
			throw new UtilException("Can not unregister bean, the factory is not a DefaultSingletonBeanRegistry!");
		}
	}

	public static void publishEvent(ApplicationEvent event) {
		if (null != applicationContext) {
			applicationContext.publishEvent(event);
		}
	}

	public static void publishEvent(Object event) {
		if (null != applicationContext) {
			applicationContext.publishEvent(event);
		}
	}

	@Override
	public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
		ApplicationContextHelper.beanFactory = beanFactory;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}