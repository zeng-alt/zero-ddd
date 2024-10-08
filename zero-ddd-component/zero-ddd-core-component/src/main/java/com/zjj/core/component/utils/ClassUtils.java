package com.zjj.core.component.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年07月05日 19:23
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClassUtils {

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
