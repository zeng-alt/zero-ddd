package com.zjj.graphql.component.supper.definition;

import com.google.common.collect.Lists;
import com.zjj.graphql.component.context.EntityContext;
import graphql.language.*;
import graphql.schema.GraphQLType;
import graphql.schema.idl.TypeDefinitionRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.graphql.execution.TypeDefinitionConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月25日 11:10
 */
@Slf4j
@RequiredArgsConstructor
public class EntityMutationDefinitionConfigurer implements TypeDefinitionConfigurer, Ordered {

    private final EntityContext entityContext;

    @Override
    public void configure(TypeDefinitionRegistry registry) {
        final List<SDLDefinition> definitions = new ArrayList<>();
        entityContext.forEachManagedType(entity -> {
            if (registry.getType(entity.getType()).isPresent()) {
                ObjectTypeExtensionDefinition.Builder mutation = ObjectTypeExtensionDefinition.newObjectTypeExtensionDefinition().name("Mutation");
                mutation.fieldDefinitions(
                        Lists.newArrayList(
                            FieldDefinition
                                    .newFieldDefinition()
                                    .name("saveAll" + entity.getType())
                                    .inputValueDefinitions(
                                            Lists.newArrayList(
                                                    InputValueDefinition
                                                        .newInputValueDefinition()
                                                        .name(entity.getInputName())
                                                        .type(ListType.newListType(entity.getInputType()).build())
                                                        .build(),
                                                    InputValueDefinition
                                                        .newInputValueDefinition()
                                                        .name("ignoringNull")
                                                        .type(TypeName.newTypeName("Boolean").build())
                                                        .defaultValue(new BooleanValue(true))
                                                        .build()
                                            )
                                    )
                                    .type(ListType.newListType(entity.getEntityType()).build())
                                    .build(),
                                FieldDefinition
                                        .newFieldDefinition()
                                        .name("save" + entity.getType())
                                        .inputValueDefinitions(
                                                Lists.newArrayList(
                                                        InputValueDefinition
                                                                .newInputValueDefinition()
                                                                .name(entity.getInputName())
                                                                .type(entity.getInputType())
                                                                .build(),
                                                        InputValueDefinition
                                                                .newInputValueDefinition()
                                                                .name("ignoringNull")
                                                                .type(TypeName.newTypeName("Boolean").build())
                                                                .defaultValue(new BooleanValue(true))
                                                                .build()
                                                )
                                        )
                                        .type(entity.getEntityType())
                                        .build(),
                                FieldDefinition
                                        .newFieldDefinition()
                                        .name("delete" + entity.getType() + "Ids")
                                        .inputValueDefinition(
                                                InputValueDefinition
                                                        .newInputValueDefinition()
                                                        .name(entity.getIdName())
                                                        .type(ListType.newListType(entity.getIdType()).build())
                                                        .build()
                                        )
                                        .type(TypeName.newTypeName("String").build())
                                        .build()
                        )
                );
                definitions.add(mutation.build());
            } else {
                log.warn("entity Type {} already exists", entity.getType());
            }
        });

        registry.addAll(definitions).ifPresent(e -> log.error(e.getMessage()));
    }


    @Override
    public int getOrder() {
        return 5;
    }
}
