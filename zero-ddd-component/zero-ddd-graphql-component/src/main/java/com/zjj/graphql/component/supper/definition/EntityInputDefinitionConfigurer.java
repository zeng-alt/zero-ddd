package com.zjj.graphql.component.supper.definition;

import com.zjj.graphql.component.context.ConditionTypeContext;
import com.zjj.graphql.component.context.EntityContext;
import com.zjj.graphql.component.context.EntityGraphqlAttribute;
import graphql.language.InputObjectTypeDefinition;
import graphql.language.InputValueDefinition;
import graphql.language.IntValue;
import graphql.language.SDLDefinition;
import graphql.schema.idl.TypeDefinitionRegistry;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.graphql.execution.TypeDefinitionConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月18日 20:04
 */
@Slf4j
@RequiredArgsConstructor
public class EntityInputDefinitionConfigurer implements TypeDefinitionConfigurer, Ordered {

//    private final EntityManager entityManager;
    private final EntityContext entityContext;
    private final ConditionTypeContext typeContext;

    @Override
    public void configure(@NonNull TypeDefinitionRegistry registry) {
        registry.scalars().forEach((k, v) -> typeContext.addType(k));


        final List<SDLDefinition> definitions = new ArrayList<>();
        InputObjectTypeDefinition.Builder pageQuery = InputObjectTypeDefinition.newInputObjectDefinition().name("PageQuery");
        pageQuery.inputValueDefinitions(List.of(
                InputValueDefinition.newInputValueDefinition().name("first").defaultValue(IntValue.of(10)).type(typeContext.get("Int")).build(),
                InputValueDefinition.newInputValueDefinition().name("after").type(typeContext.get("String")).build(),
                InputValueDefinition.newInputValueDefinition().name("last").defaultValue(IntValue.of(10)).type(typeContext.get("Int")).build(),
                InputValueDefinition.newInputValueDefinition().name("before").type(typeContext.get("String")).build()
        ));

        definitions.add(pageQuery.build());


        entityContext.forEachManagedType(entity -> {
            if (registry.getType(entity.getInputTypeName()).isEmpty()) {
                InputObjectTypeDefinition.Builder builder = InputObjectTypeDefinition.newInputObjectDefinition();
                builder.name(entity.getInputTypeName());
                for (EntityGraphqlAttribute attribute : entity.getAttributes()) {
                    InputValueDefinition.Builder valueDefinition = InputValueDefinition.newInputValueDefinition();
                    valueDefinition.name(attribute.getName());
                    valueDefinition.type(attribute.getInputType());
                    builder.inputValueDefinition(valueDefinition.build());
                }
                definitions.add(builder.build());
            } else {
                log.warn("entity Type {} already exists", entity.getInputTypeName());
            }
        });
        registry.addAll(definitions).ifPresent(e -> log.error(e.getMessage()));
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
