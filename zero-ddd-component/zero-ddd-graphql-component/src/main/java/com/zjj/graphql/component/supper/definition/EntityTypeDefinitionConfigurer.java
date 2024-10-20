package com.zjj.graphql.component.supper.definition;

import com.zjj.graphql.component.utils.TypeMatchUtils;
import graphql.language.*;
import graphql.schema.idl.TypeDefinitionRegistry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EntityType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.graphql.execution.TypeDefinitionConfigurer;

import java.util.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月18日 20:04
 */
@Slf4j
@RequiredArgsConstructor
public class EntityTypeDefinitionConfigurer implements TypeDefinitionConfigurer, Ordered {

    private final EntityManager entityManager;


    @Override
    public void configure(TypeDefinitionRegistry registry) {
        final List<SDLDefinition> definitions = new ArrayList<>();

        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        for (EntityType<?> entity : entities) {
            if (registry.getType(entity.getName()).isPresent()) {
                continue;
            }
            ObjectTypeDefinition.Builder builder = ObjectTypeDefinition.newObjectTypeDefinition();
            builder.name(entity.getName());
            for (Attribute<?, ?> attribute : entity.getAttributes()) {
                builder.fieldDefinition(
                        FieldDefinition
                                .newFieldDefinition()
                                .name(attribute.getName())
                                .type(TypeMatchUtils.structType(attribute))
                                .build()
                );
            }
            definitions.add(builder.build());
        }

        registry.addAll(definitions).ifPresent(e -> log.error(e.getMessage()));
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
