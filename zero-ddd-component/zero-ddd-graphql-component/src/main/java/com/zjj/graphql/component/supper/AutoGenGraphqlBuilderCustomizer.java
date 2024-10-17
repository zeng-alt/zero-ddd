package com.zjj.graphql.component.supper;

import com.zjj.graphql.component.utils.TypeMatchUtils;
import graphql.language.*;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.graphql.execution.GraphQlSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月17日 21:27
 */
@RequiredArgsConstructor
public class AutoGenGraphqlBuilderCustomizer implements GraphQlSourceBuilderCustomizer {

    private final EntityManager entityManager;
    private final List<Definition> definitions = new ArrayList<>();

    @Override
    public void customize(GraphQlSource.SchemaResourceBuilder builder) {
        builder.configureTypeDefinitions(c -> {
            genType();
//           c.add()
        });

    }

    private void genType() {
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        for (EntityType<?> entity : entities) {
            for (Attribute<?, ?> attribute : entity.getAttributes()) {
                TypeMatchUtils.matchType(attribute.getJavaType());
            }
//
//            definitions.add(builder);
        }

        ObjectTypeDefinition userTypeDefinition = ObjectTypeDefinition.newObjectTypeDefinition()
                .name("User")
                .fieldDefinition(FieldDefinition.newFieldDefinition()
                        .name("id")
                        .type(TypeName.newTypeName("ID").build())
                        .build())
                .fieldDefinition(FieldDefinition.newFieldDefinition()
                        .name("name")
                        .type(TypeName.newTypeName("String").build())
                        .build())
                .build();
    }

    private void genPageType() {

    }

    private void genQuery() {

    }
}
