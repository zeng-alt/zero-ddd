package com.zjj.domain.component;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.Setter;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;
import org.springframework.lang.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 21:07
 */
@Setter
@MappedSuperclass
public abstract class BaseAggregate<PK extends Serializable> extends BaseEntity<PK> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    private final @Transient List<DomainEvent> domainEvents = new ArrayList<>();

    protected void publishEvent(@NonNull DomainEvent event) {
        domainEvents.add(Objects.requireNonNull(event));
    }

    @AfterDomainEventPublication
    protected void clearDomainEvents() {
        this.domainEvents.clear();
    }

    @DomainEvents
    protected Collection<DomainEvent> domainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

}
