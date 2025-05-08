package com.zjj.gateway.advice;

import com.zjj.autoconfigure.component.json.JsonHelper;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月28日 21:11
 */
public class GateWayExceptionAdvice extends DefaultErrorWebExceptionHandler {

    private final JsonHelper jsonHelper;

    public GateWayExceptionAdvice(ErrorAttributes errorAttributes, WebProperties.Resources resources, ErrorProperties errorProperties, ApplicationContext applicationContext, JsonHelper jsonHelper) {
        super(errorAttributes, resources, errorProperties, applicationContext);
        this.jsonHelper = jsonHelper;
    }


    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Throwable error = super.getError(request);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, error.getMessage());
        if (error instanceof NotFoundException) {
            problemDetail.setStatus(HttpStatus.NOT_FOUND.value());
        } else if (error instanceof AccessDeniedException) {
            problemDetail.setStatus(HttpStatus.FORBIDDEN.value());
        } else if (error instanceof AuthenticationException) {
            problemDetail.setStatus(HttpStatus.UNAUTHORIZED.value());
        } else if (error instanceof ResponseStatusException exception) {
            problemDetail.setStatus(exception.getStatusCode().value());
        }

        problemDetail.setInstance(request.uri());
        return jsonHelper.toMap(problemDetail);
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        return (Integer) errorAttributes.get("status");
    }
}
