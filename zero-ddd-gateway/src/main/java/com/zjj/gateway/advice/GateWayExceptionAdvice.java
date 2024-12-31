package com.zjj.gateway.advice;

import com.zjj.autoconfigure.component.core.Response;
import com.zjj.autoconfigure.component.json.JsonHelper;
import com.zjj.core.component.api.ExceptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;

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
        ExceptionResponse fail = ExceptionResponse.of(error.getMessage(), request.path(), request.method().name());

        if (error instanceof NotFoundException) {
            fail = fail.code(404);
        } else if (error instanceof AccessDeniedException) {
            fail = fail.code(HttpStatus.FORBIDDEN.value());
        } else if (error instanceof AuthenticationException) {
            fail = fail.code(HttpStatus.UNAUTHORIZED.value());
        }
        return jsonHelper.toMap(fail);
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        return (int) errorAttributes.get("status");
    }
}
