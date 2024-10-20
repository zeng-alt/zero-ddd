package com.zjj.graphql.component.supper.definition;

import com.zjj.graphql.component.query.condition.Option;
import graphql.language.*;
import graphql.schema.idl.TypeDefinitionRegistry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.graphql.execution.TypeDefinitionConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zengJiaJun
 * @crateTime 2024年10月20日 21:39
 * @version 1.0
 */
@RequiredArgsConstructor
public class EntityConditionQueryDefinitionConfigurer implements TypeDefinitionConfigurer, Ordered {

    private final EntityManager entityManager;

    private static final String QUERY = "Query";
    private static final String UNCAPITALIZE_INPUT = "input";
    private static final String CAPITALIZE_INPUT = "Input";

    @Override
    public int getOrder() {
        return 7;
    }

    @Override
    public void configure(TypeDefinitionRegistry registry) {
        final List<SDLDefinition> definitions = new ArrayList<>();
        final Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        genOption(definitions);
        genCondition(definitions);
        genEntityCondition(definitions, entities);


        for (EntityType<?> entity : entities) {
            ObjectTypeExtensionDefinition.Builder query = ObjectTypeExtensionDefinition.newObjectTypeExtensionDefinition().name(QUERY);
            query.fieldDefinition(
                    FieldDefinition
                            .newFieldDefinition()
                            .name("conditionQuery" + entity.getName())
                            .inputValueDefinition(
                                    InputValueDefinition
                                            .newInputValueDefinition()
                                            .name(UNCAPITALIZE_INPUT + entity.getName())
                                            .type(TypeName.newTypeName(entity.getName() + "Condition").build())
                                            .build())
                            .type(ListType.newListType(TypeName.newTypeName(entity.getName()).build()).build())
                            .build()
            ).fieldDefinition(
                    FieldDefinition
                            .newFieldDefinition()
                            .name("conditionFind" + entity.getName())
                            .inputValueDefinition(
                                    InputValueDefinition
                                            .newInputValueDefinition()
                                            .name(UNCAPITALIZE_INPUT + entity.getName())
                                            .type(TypeName.newTypeName(entity.getName() + "Condition").build())
                                            .build())
                            .type(TypeName.newTypeName(entity.getName()).build())
                            .build()
            ).fieldDefinition(
                    FieldDefinition
                            .newFieldDefinition()
                            .name("conditionPage" + entity.getName())
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
                                            .type(TypeName.newTypeName(entity.getName() + "Condition").build())
                                            .build()
                            )
                            .type(TypeName.newTypeName(entity.getName() + "Connection").build())
                            .build()
            );
            definitions.add(query.build());



        }
        registry.addAll(definitions);
    }

    private void genEntityCondition(List<SDLDefinition> definitions, Set<EntityType<?>> entities) {
        for (EntityType<?> entity : entities) {
            String name = entity.getName();
            InputObjectTypeDefinition.Builder input = InputObjectTypeDefinition.newInputObjectDefinition().name(name + "Condition");
            for (Attribute<?, ?> attribute : entity.getAttributes()) {
                input.inputValueDefinition(InputValueDefinition.newInputValueDefinition().name(attribute.getName()).type(TypeName.newTypeName("Condition").build()).build());
            }
            definitions.add(input.build());
        }
    }

    private void genCondition(List<SDLDefinition> definitions) {
        InputObjectTypeDefinition.Builder condition = InputObjectTypeDefinition.newInputObjectDefinition().name("Condition");
        condition.inputValueDefinitions(List.of(
                InputValueDefinition.newInputValueDefinition().name("value").type(TypeName.newTypeName("String").build()).build(),
                InputValueDefinition.newInputValueDefinition().name("option").defaultValue(EnumValue.of("EQ")).type(TypeName.newTypeName("Option").build()).build()
        ));


        definitions.add(condition.build());


        InputObjectTypeDefinition.Builder conditions = InputObjectTypeDefinition.newInputObjectDefinition().name("Conditions");
        conditions.inputValueDefinition(InputValueDefinition.newInputValueDefinition().name("conditionList").type(ListType.newListType(TypeName.newTypeName("Condition").build()).build()).build());

        definitions.add(conditions.build());
    }

    private void genOption(List<SDLDefinition> definitions) {
        final EnumTypeDefinition.Builder option = EnumTypeDefinition.newEnumTypeDefinition().name("Option");
        for (Option value : Option.values()) {
            option.enumValueDefinition(EnumValueDefinition.newEnumValueDefinition().name(value.name()).build());
        }
        definitions.add(option.build());
    }
}
