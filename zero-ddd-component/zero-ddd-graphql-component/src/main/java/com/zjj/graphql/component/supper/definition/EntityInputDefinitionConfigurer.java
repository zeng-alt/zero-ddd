package com.zjj.graphql.component.supper.definition;

import com.zjj.graphql.component.utils.TypeMatchUtils;
import graphql.language.*;
import graphql.schema.idl.TypeDefinitionRegistry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EntityType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.graphql.execution.TypeDefinitionConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月18日 20:04
 */
@Slf4j
@RequiredArgsConstructor
public class EntityInputDefinitionConfigurer implements TypeDefinitionConfigurer, Ordered {

    private final EntityManager entityManager;

    @Override
    public void configure(@NonNull TypeDefinitionRegistry registry) {
        final List<SDLDefinition> definitions = new ArrayList<>();
        InputObjectTypeDefinition.Builder pageQuery = InputObjectTypeDefinition.newInputObjectDefinition().name("PageQuery");
        pageQuery.inputValueDefinitions(List.of(
                InputValueDefinition.newInputValueDefinition().name("first").defaultValue(IntValue.of(10)).type(TypeName.newTypeName("Int").build()).build(),
                InputValueDefinition.newInputValueDefinition().name("after").type(TypeName.newTypeName("String").build()).build(),
                InputValueDefinition.newInputValueDefinition().name("last").defaultValue(IntValue.of(10)).type(TypeName.newTypeName("Int").build()).build(),
                InputValueDefinition.newInputValueDefinition().name("before").type(TypeName.newTypeName("String").build()).build()
        ));

        definitions.add(pageQuery.build());

        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        for (EntityType<?> entity : entities) {
            String inputName = "Input" + entity.getName();
            if (registry.getType(inputName).isPresent()) {
                continue;
            }
            InputObjectTypeDefinition.Builder builder = InputObjectTypeDefinition.newInputObjectDefinition();
            builder.name(inputName);
            for (Attribute<?, ?> attribute : entity.getAttributes()) {
                builder.inputValueDefinition(
                        InputValueDefinition
                                .newInputValueDefinition()
                                .name(attribute.getName())
                                .type(TypeMatchUtils.structInput(attribute))
                                .build()
                );
            }
            InputObjectTypeDefinition build = builder.build();
            definitions.add(build);
        }
        registry.addAll(definitions).ifPresent(e -> log.error(e.getMessage()));
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
