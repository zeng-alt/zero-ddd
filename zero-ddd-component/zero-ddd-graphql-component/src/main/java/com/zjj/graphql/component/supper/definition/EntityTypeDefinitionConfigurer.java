package com.zjj.graphql.component.supper.definition;

import com.zjj.graphql.component.context.EntityContext;
import com.zjj.graphql.component.context.EntityGraphqlAttribute;
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

    private final EntityContext entityContext;

    @Override
    public void configure(TypeDefinitionRegistry registry) {
        final List<SDLDefinition> definitions = new ArrayList<>();

        entityContext.forEachManagedType(entity -> {
            if (registry.getType(entity.getType()).isEmpty()) {
                ObjectTypeDefinition.Builder builder = ObjectTypeDefinition.newObjectTypeDefinition();
                builder.name(entity.getType());
                for (EntityGraphqlAttribute attribute : entity.getAttributes()) {
                    builder.fieldDefinition(
                        FieldDefinition
                            .newFieldDefinition()
                            .name(attribute.getName())
                            .type(attribute.adaptType(attribute.getEntityType()))
                            .build()
                    );
                }
                definitions.add(builder.build());
            } else {
                log.warn("entity Type {} already exists", entity.getType());
            }
        });

        registry.addAll(definitions).ifPresent(e -> log.error(e.getMessage()));
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
