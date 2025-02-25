package com.zjj.memory.component.provider;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月08日 20:29
 */
public class Tuple<V> implements Comparable<Tuple<V>>, Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private final String key;

	private final V value;

	public Tuple(String key, V value) {
		this.key = key;
		this.value = value;
	}

	public static <T> Tuple<T> of(String t1, T t2) {
		return new Tuple<>(t1, t2);
	}

	public String _1() {
		return key;
	}

	public V _2() {
		return value;
	}

	public static <T extends Comparable<? super T>> int compareTo(Tuple<?> o1, Tuple<?> o2) {
		final Tuple<T> t1 = (Tuple<T>) o1;
		final Tuple<T> t2 = (Tuple<T>) o2;

		final int check1 = t1.key.compareTo(t2.key);
		if (check1 != 0) {
			return check1;
		}

		final int check2 = t1.value.compareTo(t2.value);
		if (check2 != 0) {
			return check2;
		}

		// all components are equal
		return 0;
	}

	@Override
	public int compareTo(Tuple<V> that) {
		return Tuple.compareTo(this, that);
	}

}
