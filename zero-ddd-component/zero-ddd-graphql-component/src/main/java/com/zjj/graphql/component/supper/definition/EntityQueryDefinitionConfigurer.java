package com.zjj.graphql.component.supper.definition;

import com.querydsl.core.util.BeanUtils;
import com.zjj.graphql.component.context.EntityContext;
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
public class EntityQueryDefinitionConfigurer implements TypeDefinitionConfigurer, Ordered {

    private final EntityContext entityContext;


    private static final List<InputValueDefinition> PAGE_VALUE_DEFINITIONS = List.of(
            InputValueDefinition.newInputValueDefinition().name("first").defaultValue(IntValue.of(10)).type(TypeName.newTypeName("Int").build()).build(),
            InputValueDefinition.newInputValueDefinition().name("after").type(TypeName.newTypeName("String").build()).build(),
            InputValueDefinition.newInputValueDefinition().name("last").defaultValue(IntValue.of(10)).type(TypeName.newTypeName("Int").build()).build(),
            InputValueDefinition.newInputValueDefinition().name("before").type(TypeName.newTypeName("String").build()).build()
    );

    @Override
    public void configure(@NonNull TypeDefinitionRegistry registry) {
        final List<SDLDefinition> definitions = new ArrayList<>();

        entityContext.forEachEntity(entity -> {
            ObjectTypeExtensionDefinition.Builder query = ObjectTypeExtensionDefinition.newObjectTypeExtensionDefinition().name("Query");
            query.fieldDefinition(
                    FieldDefinition
                            .newFieldDefinition()
                            .name("query" + entity.getType())
                            .inputValueDefinition(
                                    InputValueDefinition
                                            .newInputValueDefinition()
                                            .name(entity.getInputName())
                                            .type(entity.getInputType())
                                            .build())
                            .type(entity.wrapCollectionType(entity.getEntityType(), entity.getComment()))
                            .build()
            ).fieldDefinition(
                    FieldDefinition
                            .newFieldDefinition()
                            .name("find" + entity.getType())
                            .inputValueDefinition(
                                    InputValueDefinition
                                            .newInputValueDefinition()
                                            .name(entity.getInputName())
                                            .type(entity.getInputType())
                                            .build())
                            .type(entity.getEntityType())
                            .build()
            ).fieldDefinition(
                    FieldDefinition
                            .newFieldDefinition()
                            .name("page" + entity.getType())
                            .inputValueDefinitions(PAGE_VALUE_DEFINITIONS)
                            .type(TypeName.newTypeName(entity.getType() + "Connection").build())
                            .build()
            );
            definitions.add(query.build());
        });

        registry.addAll(definitions);
    }

    @Override
    public int getOrder() {
        return 4;
    }
}
