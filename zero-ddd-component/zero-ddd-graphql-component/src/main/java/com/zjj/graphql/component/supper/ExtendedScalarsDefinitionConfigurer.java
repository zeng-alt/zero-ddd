package com.zjj.graphql.component.supper;

import com.google.common.collect.Lists;
import com.querydsl.core.util.ReflectionUtils;
import com.zjj.core.component.type.TypeConversionHelper;
import graphql.language.*;
import graphql.scalars.ExtendedScalars;
import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;
import graphql.schema.idl.TypeDefinitionRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.execution.TypeDefinitionConfigurer;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月17日 21:57
 */
@Slf4j
public class ExtendedScalarsDefinitionConfigurer implements TypeDefinitionConfigurer {




    @Override
    public void configure(TypeDefinitionRegistry registry) {


        List<SDLDefinition> definitions = new ArrayList<>();
        Set<Field> fields = ReflectionUtils.getFields(ExtendedScalars.class);
        fields.stream()
                .map(Field::getName)
                .map(n -> {
                    if (n.startsWith("GraphQL")) {
                        return n.replace("GraphQL", "");
                    } else if ("Json".equals(n)) {
                        return "JSON";
                    } else  {
                        return n;
                    }
                })
                .map(n -> ScalarTypeDefinition.newScalarTypeDefinition().name(n).build())
                .forEach(definitions::add);

        definitions.add(ScalarTypeDefinition.newScalarTypeDefinition().name("LocalDateTime").build());

        // add targetType
        registry.add(getTargetType());
        registry.addAll(definitions);
        log.info("组装Scalars类型");
    }

    private SDLDefinition getTargetType() {
        List<EnumValueDefinition> enumValueDefinitions = new ArrayList<>();
        Set<String> types = TypeConversionHelper.getTypes();
        for (String type : types) {
            enumValueDefinitions.add(
                    EnumValueDefinition
                            .newEnumValueDefinition()
                            .name(type)
                            .build()
            );
        }

        return EnumTypeDefinition
                .newEnumTypeDefinition()
                .name("TargetType")
                .enumValueDefinitions(enumValueDefinitions)
                .build();
    }
}
