package com.zjj.graphql.component.supper.definition;

import com.zjj.graphql.component.context.EntityContext;
import graphql.language.*;
import graphql.schema.idl.TypeDefinitionRegistry;
import jakarta.persistence.EntityManager;
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
 * @crateTime 2024年10月18日 21:07
 */
@Slf4j
@RequiredArgsConstructor
public class EntityFuzzyQueryDefinitionConfigurer implements TypeDefinitionConfigurer, Ordered {


    private final EntityContext entityContext;


    @Override
    public void configure(@NonNull TypeDefinitionRegistry registry) {
        final List<SDLDefinition> definitions = new ArrayList<>();
        entityContext.forEachEntity(entity -> {

            ObjectTypeExtensionDefinition.Builder query = ObjectTypeExtensionDefinition.newObjectTypeExtensionDefinition().name("Query");
            query.fieldDefinition(
                    FieldDefinition
                            .newFieldDefinition()
                            .name("fuzzyQuery" + entity.getType())
                            .inputValueDefinition(
                                    InputValueDefinition
                                            .newInputValueDefinition()
                                            .name(entity.getInputName())
                                            .type(entity.getInputType())
                                            .build())
                            .inputValueDefinition(
                                    InputValueDefinition
                                            .newInputValueDefinition()
                                            .name("sort")
                                            .type(ListType.newListType(TypeName.newTypeName("Order").build()).build())
                                            .build()
                            )
                            .type(entity.wrapCollectionType(entity.getEntityType(), "查询" + entity.getName() + "列表"))
                            .build()
            ).fieldDefinition(
                    FieldDefinition
                            .newFieldDefinition()
                            .name("fuzzyFind" + entity.getType())
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
                            .name("fuzzyPage" + entity.getType())
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
                                            .type(ListType.newListType(TypeName.newTypeName("Order").build()).build())
                                            .build()
                            )
                            .inputValueDefinition(
                                    InputValueDefinition
                                            .newInputValueDefinition()
                                            .name(entity.getInputName())
                                            .type(entity.getInputType())
                                            .build()
                            )
                            .type(TypeName.newTypeName(entity.getType() + "Connection").build())
                            .build()
            );
            definitions.add(query.build());
        });
        registry.addAll(definitions).ifPresent(e -> log.error(e.getMessage()));
    }

    @Override
    public int getOrder() {
        return 4;
    }
}
