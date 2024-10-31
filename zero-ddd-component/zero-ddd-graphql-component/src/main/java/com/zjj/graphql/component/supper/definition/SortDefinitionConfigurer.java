package com.zjj.graphql.component.supper.definition;

import graphql.language.*;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.core.Ordered;
import org.springframework.graphql.execution.TypeDefinitionConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengJiaJun
 * @crateTime 2024年10月19日 20:36
 * @version 1.0
 */
public class SortDefinitionConfigurer implements TypeDefinitionConfigurer, Ordered {
    @Override
    public int getOrder() {
        return 20;
    }

    @Override
    public void configure(TypeDefinitionRegistry registry) {
        final List<SDLDefinition> definitions = new ArrayList<>();

        final EnumTypeDefinition directionDefinition = EnumTypeDefinition.newEnumTypeDefinition()
                .name("Direction")
                .enumValueDefinition(EnumValueDefinition.newEnumValueDefinition().name("ASC").build())
                .enumValueDefinition(EnumValueDefinition.newEnumValueDefinition().name("DESC").build())
                .build();

        final EnumTypeDefinition nullHandlingDefinition = EnumTypeDefinition.newEnumTypeDefinition()
                .name("NullHandling")
                .enumValueDefinition(EnumValueDefinition.newEnumValueDefinition().name("NATIVE").build())
                .enumValueDefinition(EnumValueDefinition.newEnumValueDefinition().name("NULLS_FIRST").build())
                .enumValueDefinition(EnumValueDefinition.newEnumValueDefinition().name("NULLS_LAST").build())
                .build();

        final InputObjectTypeDefinition orderDefinition = InputObjectTypeDefinition.newInputObjectDefinition()
                .name("Order")
                .inputValueDefinition(InputValueDefinition.newInputValueDefinition().name("property").type(TypeName.newTypeName("String").build()).build())
                .inputValueDefinition(InputValueDefinition.newInputValueDefinition().name("direction").type(TypeName.newTypeName("Direction").build()).build())
                .inputValueDefinition(InputValueDefinition.newInputValueDefinition().name("ignoreCase").type(TypeName.newTypeName("Boolean").build()).defaultValue(BooleanValue.of(false)).build())
                .inputValueDefinition(InputValueDefinition.newInputValueDefinition().name("nullHandling").type(TypeName.newTypeName("NullHandling").build()).defaultValue(EnumValue.of("NATIVE")).build())
                .build();



        definitions.add(nullHandlingDefinition);
        definitions.add(directionDefinition);
        definitions.add(orderDefinition);

        registry.addAll(definitions);
    }
}
