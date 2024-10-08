package com.zjj.domain.component;

import com.zjj.bean.componenet.ApplicationContextHelper;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月27日 20:31
 */
public interface AggregateRoot<T> {

	default void publishEvent(DomainEvent event) {
		ApplicationContextHelper.publishEvent(event);
	}

}
