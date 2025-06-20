package com.zjj.graphql.component.config;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.DataFetcherExceptionResolver;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月03日 16:27
 */
@Slf4j
@ControllerAdvice
public class GraphQLExceptionHandler implements DataFetcherExceptionResolver {

    @GraphQlExceptionHandler(Exception.class)
    public GraphQLError handle(Exception ex) {
        return GraphqlErrorBuilder.newError()
                .message("服务错误: " + ex.getMessage())
                .errorType(ErrorType.INTERNAL_ERROR)
                .build();
    }

    @GraphQlExceptionHandler(RuntimeException.class)
    public GraphQLError handle(RuntimeException ex) {
        return GraphqlErrorBuilder.newError()
                .message("业务异常: " + ex.getMessage())
                .errorType(ErrorType.BAD_REQUEST)
                .build();
    }

    @GraphQlExceptionHandler(ValidationException.class)
    public GraphQLError handle(ValidationException ex) {
        return GraphqlErrorBuilder.newError()
                .message("校验错误: " + ex.getMessage())
                .errorType(ErrorType.BAD_REQUEST)
                .build();
    }

    @Override
    public Mono<List<GraphQLError>> resolveException(Throwable ex, DataFetchingEnvironment env) {
        GraphqlErrorBuilder<?> builder = GraphqlErrorBuilder.newError(env)
                .message(ex.getMessage());
        if (ex instanceof RuntimeException) {
            builder.errorType(ErrorType.BAD_REQUEST);
        } else if (ex instanceof Exception) {
            builder.errorType(ErrorType.INTERNAL_ERROR);
        }
        log.error("", ex);
        return Mono.just(List.of(builder.build()));
    }
}
