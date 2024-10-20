package com.zjj.graphql.component.supper.definition;

import graphql.language.*;
import graphql.schema.idl.TypeDefinitionRegistry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.graphql.execution.TypeDefinitionConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月18日 21:07
 */
@RequiredArgsConstructor
public class EntityFuzzyQueryDefinitionConfigurer implements TypeDefinitionConfigurer, Ordered {

    private final EntityManager entityManager;

    private static final String QUERY = "Query";
    private static final String UNCAPITALIZE_INPUT = "input";
    private static final String CAPITALIZE_INPUT = "Input";

    @Override
    public void configure(@NonNull TypeDefinitionRegistry registry) {
        final List<SDLDefinition> definitions = new ArrayList<>();
        final Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        for (EntityType<?> entity : entities) {
            ObjectTypeExtensionDefinition.Builder query = ObjectTypeExtensionDefinition.newObjectTypeExtensionDefinition().name(QUERY);
            query.fieldDefinition(
                    FieldDefinition
                            .newFieldDefinition()
                            .name("fuzzyQuery" + entity.getName())
                            .inputValueDefinition(
                                    InputValueDefinition
                                            .newInputValueDefinition()
                                            .name(UNCAPITALIZE_INPUT + entity.getName())
                                            .type(TypeName.newTypeName(CAPITALIZE_INPUT + entity.getName()).build())
                                            .build())
                            .type(ListType.newListType(TypeName.newTypeName(entity.getName()).build()).build())
                            .build()
            ).fieldDefinition(
                    FieldDefinition
                            .newFieldDefinition()
                            .name("fuzzyFind" + entity.getName())
                            .inputValueDefinition(
                                    InputValueDefinition
                                            .newInputValueDefinition()
                                            .name(UNCAPITALIZE_INPUT + entity.getName())
                                            .type(TypeName.newTypeName(CAPITALIZE_INPUT + entity.getName()).build())
                                            .build())
                            .type(TypeName.newTypeName(entity.getName()).build())
                            .build()
            ).fieldDefinition(
                    FieldDefinition
                            .newFieldDefinition()
                            .name("fuzzyPage" + entity.getName())
                            .inputValueDefinition(
                                    InputValueDefinition
                                            .newInputValueDefinition()
                                            .name("pageQuery")
                                            .type(TypeName.newTypeName("PageQuery").build())
                                            .build()
                            )
                            .inputValueDefinition(
                                    InputValueDefinition
                                            .newInputValueDefinition()
                                            .name("sort")
                                            .type(TypeName.newTypeName("Sort").build())
                                            .build()
                            )
                            .inputValueDefinition(
                                    InputValueDefinition
                                            .newInputValueDefinition()
                                            .name(UNCAPITALIZE_INPUT + entity.getName())
                                            .type(TypeName.newTypeName(CAPITALIZE_INPUT + entity.getName()).build())
                                            .build()
                            )
                            .type(TypeName.newTypeName(entity.getName() + "Connection").build())
                            .build()
            );
            definitions.add(query.build());



        }
        registry.addAll(definitions);
    }

    @Override
    public int getOrder() {
        return 4;
    }
}
