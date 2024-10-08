package com.zjj.excel.component.domain;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年07月23日 20:44
 */
@FunctionalInterface
public interface InvokeFunction<T> {

	void invoke(T t);

	static <T> InvokeFunction<T> withDefaults() {
		return (t) -> {
		};
	}

}
