package com.zjj.graphql.component.supper;

import com.querydsl.core.util.ReflectionUtils;
import graphql.language.*;
import graphql.scalars.ExtendedScalars;
import graphql.schema.idl.TypeDefinitionRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.execution.TypeDefinitionConfigurer;

import java.lang.reflect.Field;
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

        registry.addAll(definitions);
        log.info("组装Scalars类型");
    }
}
