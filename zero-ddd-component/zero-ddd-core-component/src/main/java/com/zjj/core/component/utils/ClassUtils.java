package com.zjj.core.component.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.MergedAnnotations;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年07月05日 19:23
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClassUtils {


	public static Type findGenericType(Class<?> clazz, Class<?> ... filterClass) {
		Type type = null;
		while (type == null && clazz != null) {
			List<Type> allType = findAllType(clazz);
			for (Type genericInterface : allType) {
				Class<?> rawClass = ResolvableType.forType(genericInterface).getRawClass();
				if (rawClass == null) {
					continue;
				}
				if (Arrays.asList(filterClass).contains(rawClass)) {
					type = genericInterface;
				}
			}
			clazz = clazz.getSuperclass();
		}
		return type;
	}

	public static List<Type> findAllType(Class<?> clazz) {
		List<Type> result = new ArrayList<>();
		if (clazz == null) return result;

		Type genericSuperclass = clazz.getGenericSuperclass();
		if (genericSuperclass != null) {
			result.add(genericSuperclass);
		}

		Type[] genericInterfaces = clazz.getGenericInterfaces();
		if (!ArrayUtils.isEmpty(genericInterfaces)) {
            Collections.addAll(result, genericInterfaces);
		}

		return result;
	}

	/**
	 * 获取指定类实现的泛型接口的实际类型
	 *
	 * @param clazz 类
	 * @param interfaceClass 接口类型
	 * @return 泛型类型的 Class 对象，或者 null 如果没有找到
	 */
	public static Class<?> findGenericTypeClass(Class<?> clazz, Class<?> interfaceClass) {
		Type[] types = clazz.getGenericSuperclass() != null ? ArrayUtils.toArray(clazz.getGenericSuperclass()) : clazz.getGenericInterfaces();
		while (!ArrayUtils.isEmpty(types)) {
			for (Type type : types) {
			}


		}

		// 遍历类和其父类的接口
		while (clazz != null) {
			// 获取该类实现的所有接口
			Type[] genericInterfaces = clazz.getGenericInterfaces();
			for (Type genericInterface : genericInterfaces) {
				Class<?> rawClass = ResolvableType.forType(genericInterface).getRawClass();
				if (rawClass == null) {
					continue;
				}
				if (interfaceClass.isAssignableFrom(rawClass)) {
					if (genericInterface instanceof ParameterizedType type) {
						return type.getActualTypeArguments()[0].getClass();
					}
				}
			}
			// 向上查找父类
			clazz = clazz.getSuperclass();
		}
		return null; // 没有找到泛型类型
	}

	public static List<Class<?>> findClasses(String packageName) {
		List<Class<?>> classes = new ArrayList<>();

		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			String path = packageName.replace('.', '/');
			Enumeration<URL> resources = classLoader.getResources(path);

			while (resources.hasMoreElements()) {
				URL resource = resources.nextElement();
				File directory = new File(resource.getFile());

				if (directory.exists() && directory.isDirectory()) {
					findClassesInDirectory(packageName, directory, classes);
				}
			}
		}
		catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return classes;
	}

	private static void findClassesInDirectory(String packageName, File directory, List<Class<?>> classes)
			throws ClassNotFoundException {
		File[] files = directory.listFiles();

		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					String subPackageName = packageName + '.' + file.getName();
					findClassesInDirectory(subPackageName, file, classes);
				}
				else if (file.getName().endsWith(".class")) {
					String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
					Class<?> clazz = Class.forName(className);
					classes.add(clazz);
				}
			}
		}
	}

}
