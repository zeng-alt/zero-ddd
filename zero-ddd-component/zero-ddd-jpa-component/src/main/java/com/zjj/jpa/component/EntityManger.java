package com.zjj.jpa.component;

import com.zjj.core.component.utils.ClassUtils;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年07月04日 17:18
 */
@Log4j2
@Component
public class EntityManger implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// 拿到所有的类上有@Entity

		final List<Class<?>> classes = ClassUtils.findClasses("com.zjj");

		final Set<Class<?>> collect = classes.stream().filter(cls -> cls.isAnnotationPresent(Entity.class))
				.filter(cls -> !cls.isInterface()).filter(cls -> !Modifier.isAbstract(cls.getModifiers()))
				.filter(JpaBaseEntity.class::isAssignableFrom).collect(Collectors.toSet());

		// 把collect转换成JpaBaseEntity,并注入到spring容器
		collect.forEach(cls -> {
			try {
				beanFactory.registerSingleton("jpa" + cls.getSimpleName(), cls.getDeclaredConstructor().newInstance());
			}
			catch (Exception e) {
				log.error(e.getCause());
				throw new RuntimeException();
			}
		});

		// Map<String, JpaBaseEntity> beansOfType =
		// beanFactory.getBeansOfType(JpaBaseEntity.class);
		// System.out.println();
	}

}
