package com.zjj.domain.component;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月27日 19:55
 */
@Getter
public abstract class DomainEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    protected final Long time;
    protected final String id;

    protected DomainEvent() {
        this.time = System.currentTimeMillis();
        this.id = UUID.randomUUID().toString();
    }
}
