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
public abstract class DomainEvent extends ApplicationEvent implements org.jmolecules.event.types.DomainEvent, Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	protected final String eventId;

	protected DomainEvent(Object o) {
		super(o);
		this.eventId = UUID.randomUUID().toString();
	}

}
