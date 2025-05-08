package com.zjj.graphql.component.supper.definition;

import com.zjj.graphql.component.context.*;
import com.zjj.graphql.component.query.condition.Option;
import graphql.language.*;
import graphql.schema.idl.TypeDefinitionRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.graphql.execution.TypeDefinitionConfigurer;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author zengJiaJun
 * @crateTime 2024年10月20日 21:39
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class EntityConditionQueryDefinitionConfigurer implements TypeDefinitionConfigurer, Ordered {

    private final EntityContext entityContext;
    private final ConditionTypeContext typeContext;

    @Override
    public int getOrder() {
        return 7;
    }

    @Override
    public void configure(@NonNull TypeDefinitionRegistry registry) {
        final List<SDLDefinition> definitions = new ArrayList<>();

        genOption(definitions);
        genCondition(definitions);
        genTypeCondition(registry);
        genEntityCondition(definitions, entityContext.getManagedType());

        for (EntityGraphqlType entity : entityContext.getEntity()) {
            ObjectTypeExtensionDefinition.Builder query = ObjectTypeExtensionDefinition.newObjectTypeExtensionDefinition().name("Query");
            query.fieldDefinition(
                    FieldDefinition
                            .newFieldDefinition()
                            .name("conditionQuery" + entity.getType())
                            .inputValueDefinition(
                                    InputValueDefinition
                                            .newInputValueDefinition()
                                            .name(entity.getConditionName())
                                            .type(entity.getConditionType())
                                            .build())
                            .inputValueDefinition(
                                    InputValueDefinition
                                            .newInputValueDefinition()
                                            .name("sort")
                                            .type(ListType.newListType(TypeName.newTypeName("Order").build()).build())
                                            .build()
                            )
                            .type(entity.wrapCollectionType(entity.getEntityType(), "条件查询" + entity.getName() + "列表"))
                            .build()
            ).fieldDefinition(
                    FieldDefinition
                            .newFieldDefinition()
                            .name("conditionFind" + entity.getType())
                            .inputValueDefinition(
                                    InputValueDefinition
                                            .newInputValueDefinition()
                                            .name(entity.getConditionName())
                                            .type(entity.getConditionType())
                                            .build())
                            .type(entity.getEntityType())
                            .build()
            ).fieldDefinition(
                    FieldDefinition
                            .newFieldDefinition()
                            .name("conditionPage" + entity.getType())
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
                                            .name("filter")
                                            .type(entity.getConditionType())
                                            .build()
                            )
                            .type(entity.getConnectionType())
                            .build()
            );
            definitions.add(query.build());



        }

        registry.addAll(definitions).ifPresent(e -> log.error(e.getMessage()));
    }

    private void genTypeCondition(TypeDefinitionRegistry registry) {
        registry.scalars().forEach((k, v) -> {
            final InputObjectTypeDefinition.Builder condition = InputObjectTypeDefinition.newInputObjectDefinition().name(k + GraphqlType.CAPITALIZE_CONDITION);
            condition.inputValueDefinitions(List.of(
                    InputValueDefinition.newInputValueDefinition().name("value").type(ListType.newListType(typeContext.get(k)).build()).build(),
                    InputValueDefinition.newInputValueDefinition().name("option").defaultValue(EnumValue.of("EQ")).type(typeContext.get("Option")).build()
            ));
            typeContext.addType(k + GraphqlType.CAPITALIZE_CONDITION);
            registry.add(condition.build()).ifPresent(e -> log.error(e.getMessage()));
        });
    }

    private void genEntityCondition(List<SDLDefinition> definitions, Collection<EntityGraphqlType<?>> entities) {
        for (EntityGraphqlType<?> entity : entities) {
            InputObjectTypeDefinition.Builder input = InputObjectTypeDefinition.newInputObjectDefinition().name(entity.getConditionTypeName());
            for (EntityGraphqlAttribute attribute : entity.getAttributes()) {
                InputValueDefinition.Builder builder = InputValueDefinition
                        .newInputValueDefinition()
                        .name(attribute.getName());

                if (attribute.getAssociation() || attribute.getEmbedded()) {
                    builder.type(entity.getConditionType());
                } else {
                    builder.type(typeContext.get(attribute.getType() + GraphqlType.CAPITALIZE_CONDITION));
                }

                input.inputValueDefinition(builder.build());
            }
            definitions.add(input.build());
        }
    }

    private void genCondition(List<SDLDefinition> definitions) {
        InputObjectTypeDefinition.Builder condition = InputObjectTypeDefinition.newInputObjectDefinition().name(GraphqlType.CAPITALIZE_CONDITION);
        condition.inputValueDefinitions(List.of(
                InputValueDefinition.newInputValueDefinition().name("value").type(typeContext.get("String")).build(),
                InputValueDefinition.newInputValueDefinition().name("option").defaultValue(EnumValue.of("EQ")).type(typeContext.get("Option")).build()
        ));
        typeContext.addType(GraphqlType.CAPITALIZE_CONDITION);
        definitions.add(condition.build());
    }

    private void genOption(List<SDLDefinition> definitions) {
        final EnumTypeDefinition.Builder option = EnumTypeDefinition.newEnumTypeDefinition().name("Option");
        for (Option value : Option.values()) {
            option.enumValueDefinition(EnumValueDefinition.newEnumValueDefinition().name(value.name()).build());
        }
        typeContext.addType("Option");
        definitions.add(option.build());
    }
}
