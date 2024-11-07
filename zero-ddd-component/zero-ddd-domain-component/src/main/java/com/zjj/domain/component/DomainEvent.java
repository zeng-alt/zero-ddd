package com.zjj.domain.component;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月27日 19:55
 */
@Getter
public abstract class DomainEvent extends ApplicationEvent implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	protected final String id;

	protected DomainEvent(Object o) {
		super(o);
		this.id = UUID.randomUUID().toString();
	}

}
