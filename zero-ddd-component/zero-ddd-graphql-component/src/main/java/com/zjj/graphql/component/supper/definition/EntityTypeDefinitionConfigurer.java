package com.zjj.graphql.component.supper.definition;

import com.zjj.graphql.component.utils.TypeMatchUtils;
import graphql.language.*;
import graphql.schema.idl.TypeDefinitionRegistry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.graphql.execution.TypeDefinitionConfigurer;

import java.util.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月18日 20:04
 */
@RequiredArgsConstructor
public class EntityTypeDefinitionConfigurer implements TypeDefinitionConfigurer, Ordered {

    private final EntityManager entityManager;


    @Override
    public void configure(TypeDefinitionRegistry registry) {
        final List<SDLDefinition> definitions = new ArrayList<>();

        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        for (EntityType<?> entity : entities) {
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

        registry.addAll(definitions);
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
