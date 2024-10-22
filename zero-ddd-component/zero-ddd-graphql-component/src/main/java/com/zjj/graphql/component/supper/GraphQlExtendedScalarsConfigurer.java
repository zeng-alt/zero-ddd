package com.zjj.graphql.component.supper;

import com.querydsl.core.util.ReflectionUtils;
import com.zjj.graphql.component.exception.ScalarFileReadException;
import graphql.language.StringValue;
import graphql.scalars.ExtendedScalars;
import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;
import graphql.schema.idl.RuntimeWiring;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月17日 21:45
 */
@Slf4j
public class GraphQlExtendedScalarsConfigurer implements RuntimeWiringConfigurer {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final GraphQLScalarType localDateTime = GraphQLScalarType.newScalar().name("LocalDateTime").description("DataTime scalar").coercing(new Coercing<LocalDateTime, String>() {
        @Override
        public String serialize(Object input) {
            LocalDateTime date = (LocalDateTime) input;
            return date.format(formatter);
        }

        @Override
        public LocalDateTime parseValue(Object input) {
            return LocalDateTime.parse(((StringValue) input).getValue(), formatter);
        }

        @Override
        public LocalDateTime parseLiteral(Object input) {
            return LocalDateTime.parse(((StringValue) input).getValue(), formatter);
        }
    }).build();

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
        builder.scalar(localDateTime);
        log.info("组装Scalars类型");
    }
}
