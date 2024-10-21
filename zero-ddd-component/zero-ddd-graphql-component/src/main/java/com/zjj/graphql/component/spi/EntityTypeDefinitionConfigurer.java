package com.zjj.graphql.component.spi;

import com.zjj.graphql.component.context.EntityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.execution.TypeDefinitionConfigurer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月21日 20:33
 */
@RequiredArgsConstructor
public abstract class EntityTypeDefinitionConfigurer implements TypeDefinitionConfigurer {

    public final EntityContext entityContext;
}
