package com.zjj.graphql.component.supper.definition;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.graphql.execution.TypeDefinitionConfigurer;

/**
 * @author zengJiaJun
 * @crateTime 2024年10月19日 21:23
 * @version 1.0
 */
public abstract class AbstractEntityDefinitionConfigurer implements TypeDefinitionConfigurer, Ordered {

    protected final EntityManager entityManager;


    protected AbstractEntityDefinitionConfigurer(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
