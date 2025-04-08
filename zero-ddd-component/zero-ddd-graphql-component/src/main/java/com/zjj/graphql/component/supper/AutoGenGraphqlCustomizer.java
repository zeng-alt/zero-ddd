package com.zjj.graphql.component.supper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.graphql.execution.ConnectionTypeDefinitionConfigurer;
import org.springframework.graphql.execution.GraphQlSource;
import org.springframework.graphql.execution.TypeDefinitionConfigurer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月17日 21:27
 */
@RequiredArgsConstructor
public class AutoGenGraphqlCustomizer implements GraphQlSourceBuilderCustomizer {


    private final ObjectProvider<TypeDefinitionConfigurer> definitions;

    @Override
    public void customize(GraphQlSource.SchemaResourceBuilder builder) {
        builder.configureTypeDefinitions(new ExtendedScalarsDefinitionConfigurer());
        definitions.orderedStream().forEach(builder::configureTypeDefinitions);
        if (definitions.stream().count() > 1) {
            builder.configureTypeDefinitions(new ConnectionTypeDefinitionConfigurer());
        }
    }
}
