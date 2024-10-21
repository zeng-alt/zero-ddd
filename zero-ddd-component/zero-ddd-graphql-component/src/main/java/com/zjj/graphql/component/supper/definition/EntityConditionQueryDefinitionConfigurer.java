package com.zjj.graphql.component.supper.definition;

import com.zjj.graphql.component.context.EntityContext;
import com.zjj.graphql.component.context.EntityGraphqlAttribute;
import com.zjj.graphql.component.context.EntityGraphqlType;
import com.zjj.graphql.component.context.GraphqlType;
import com.zjj.graphql.component.query.condition.Option;
import graphql.language.*;
import graphql.schema.idl.TypeDefinitionRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.graphql.execution.TypeDefinitionConfigurer;

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

//    private final EntityManager entityManager;
    private final EntityContext entityContext;

    private final InputObjectTypeDefinition.Builder condition = InputObjectTypeDefinition.newInputObjectDefinition().name(GraphqlType.CAPITALIZE_CONDITION).inputValueDefinition(
            InputValueDefinition.newInputValueDefinition().name("option").defaultValue(EnumValue.of("EQ")).type(TypeName.newTypeName("Option").build()).build()
    );

    @Override
    public int getOrder() {
        return 7;
    }

    @Override
    public void configure(TypeDefinitionRegistry registry) {
        final List<SDLDefinition> definitions = new ArrayList<>();

        genOption(definitions);
        genCondition(definitions);
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
                                            .type(TypeName.newTypeName("Sort").build())
                                            .build()
                            )
                            .inputValueDefinition(
                                    InputValueDefinition
                                            .newInputValueDefinition()
                                            .name(entity.getConditionName())
                                            .type(entity.getConditionType())
                                            .build()
                            )
                            .type(entity.getConnectionType())
                            .build()
            );
            definitions.add(query.build());



        }

//        for (EntityType<?> entity : entities) {
//            ObjectTypeExtensionDefinition.Builder query = ObjectTypeExtensionDefinition.newObjectTypeExtensionDefinition().name("Query");
//            query.fieldDefinition(
//                    FieldDefinition
//                            .newFieldDefinition()
//                            .name("conditionQuery" + entity.getName())
//                            .inputValueDefinition(
//                                    InputValueDefinition
//                                            .newInputValueDefinition()
//                                            .name(UNCAPITALIZE_INPUT + entity.getName())
//                                            .type(TypeName.newTypeName(entity.getName() + "Condition").build())
//                                            .build())
//                            .type(ListType.newListType(TypeName.newTypeName(entity.getName()).build()).build())
//                            .build()
//            ).fieldDefinition(
//                    FieldDefinition
//                            .newFieldDefinition()
//                            .name("conditionFind" + entity.getName())
//                            .inputValueDefinition(
//                                    InputValueDefinition
//                                            .newInputValueDefinition()
//                                            .name(UNCAPITALIZE_INPUT + entity.getName())
//                                            .type(TypeName.newTypeName(entity.getName() + "Condition").build())
//                                            .build())
//                            .type(TypeName.newTypeName(entity.getName()).build())
//                            .build()
//            ).fieldDefinition(
//                    FieldDefinition
//                            .newFieldDefinition()
//                            .name("conditionPage" + entity.getName())
//                            .inputValueDefinition(
//                                    InputValueDefinition
//                                            .newInputValueDefinition()
//                                            .name("pageQuery")
//                                            .type(TypeName.newTypeName("PageQuery").build())
//                                            .build()
//                            )
//                            .inputValueDefinition(
//                                    InputValueDefinition
//                                            .newInputValueDefinition()
//                                            .name("sort")
//                                            .type(TypeName.newTypeName("Sort").build())
//                                            .build()
//                            )
//                            .inputValueDefinition(
//                                    InputValueDefinition
//                                            .newInputValueDefinition()
//                                            .name(UNCAPITALIZE_INPUT + entity.getName())
//                                            .type(TypeName.newTypeName(entity.getName() + "Condition").build())
//                                            .build()
//                            )
//                            .type(TypeName.newTypeName(entity.getName() + "Connection").build())
//                            .build()
//            );
//            definitions.add(query.build());
//
//
//
//        }
        registry.addAll(definitions).ifPresent(e -> log.error(e.getMessage()));
    }

    private void genEntityCondition(List<SDLDefinition> definitions, Collection<EntityGraphqlType> entities) {
        for (EntityGraphqlType entity : entities) {
            InputObjectTypeDefinition.Builder input = InputObjectTypeDefinition.newInputObjectDefinition().name(entity.getConditionTypeName());
            for (EntityGraphqlAttribute attribute : entity.getAttributes()) {
                InputValueDefinition.Builder builder = InputValueDefinition
                        .newInputValueDefinition()
                        .name(attribute.getName());

                if (attribute.getAssociation() || attribute.getEmbedded()) {
                    builder.type(entity.getConditionType());
                } else {
                    builder.type(TypeName.newTypeName(GraphqlType.CAPITALIZE_CONDITION).build());
                }

                input.inputValueDefinition(builder.build());
            }
            definitions.add(input.build());
        }
    }

    private void genCondition(List<SDLDefinition> definitions) {
        InputObjectTypeDefinition.Builder condition = InputObjectTypeDefinition.newInputObjectDefinition().name(GraphqlType.CAPITALIZE_CONDITION);
        condition.inputValueDefinitions(List.of(
                InputValueDefinition.newInputValueDefinition().name("value").type(TypeName.newTypeName("String").build()).build(),
                InputValueDefinition.newInputValueDefinition().name("option").defaultValue(EnumValue.of("EQ")).type(TypeName.newTypeName("Option").build()).build()
        ));

        definitions.add(condition.build());
    }

    private void genOption(List<SDLDefinition> definitions) {
        final EnumTypeDefinition.Builder option = EnumTypeDefinition.newEnumTypeDefinition().name("Option");
        for (Option value : Option.values()) {
            option.enumValueDefinition(EnumValueDefinition.newEnumValueDefinition().name(value.name()).build());
        }
        definitions.add(option.build());
    }
}
