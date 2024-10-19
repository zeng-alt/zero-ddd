package com.zjj.graphql.component.supper.definition;

import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.core.Ordered;
import org.springframework.graphql.execution.TypeDefinitionConfigurer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月18日 21:08
 */

public class EntityMutationDefinitionConfigurer implements TypeDefinitionConfigurer, Ordered {

    @Override
    public void configure(TypeDefinitionRegistry registry) {

    }

    @Override
    public int getOrder() {
        return 5;
    }
}
