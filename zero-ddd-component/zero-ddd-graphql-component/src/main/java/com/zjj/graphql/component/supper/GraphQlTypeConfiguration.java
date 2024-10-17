package com.zjj.graphql.component.supper;

import com.querydsl.core.util.ReflectionUtils;
import com.zjj.graphql.component.exception.ScalarFileReadException;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import graphql.schema.idl.RuntimeWiring;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月17日 21:45
 */
@Slf4j
public class GraphQlTypeConfiguration implements RuntimeWiringConfigurer {

    @Override
    public void configure(RuntimeWiring.Builder builder) {

        Set<Field> fields = ReflectionUtils.getFields(ExtendedScalars.class);
        for (Field f : fields) {
            try {
                GraphQLScalarType o = (GraphQLScalarType) f.get(null);
                builder.scalar(o);
            } catch (IllegalAccessException e) {
                throw new ScalarFileReadException(e);
            }
        }
        log.info("组装Scalars类型");
    }
}
