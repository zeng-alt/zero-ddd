package com.zjj.core.component.advice;

import com.zjj.autoconfigure.component.core.ResponseAdviceProvider;
import com.zjj.autoconfigure.component.json.JsonHelper;
import com.zjj.autoconfigure.component.core.Response;
import com.zjj.autoconfigure.component.core.ResponseEnum;
import com.zjj.core.component.api.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import static io.vavr.API.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月05日 21:09
 */
@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalResultAdvice implements ResponseBodyAdvice<Object> {

    private final JsonHelper jsonHelper;
    private final ObjectProvider<ResponseAdviceProvider> responseAdviceProvider;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof String rest) {
            ((ServletServerHttpResponse) response).getServletResponse().setContentType(MediaType.APPLICATION_JSON_VALUE);
            String[] split = rest.split(":");
            String message = split.length > 1 ? split[1] : split[0];
            Response serializableResponse = Match(rest).of(
                    Case($(r -> r.contains("ok")), Response.success().message(message)),
                    Case($(r -> r.contains("fail")), Response.fail().message(message)),
                    Case($(r -> r.contains("success")), Response.success().message(message)),
                    Case($(r -> r.contains("warn")), Response.warn().message(message)),
                    Case($(), () -> null)
            );
            if (serializableResponse == null) {
                return body;
            }
            return jsonHelper.toJsonString(handler(serializableResponse));
        }

        if (body instanceof ResponseEnum responseEnum) {
            return handler(Response.apply(responseEnum));
        }

        if (body instanceof ExceptionResponse) {
            return body;
        }

        if (body instanceof Response<?> result) {
            return handler(result);
        }

        return body;
    }

    public Response handler(Response response) {
        Response result = response;
        for (ResponseAdviceProvider adviceProvider : responseAdviceProvider) {
            result = adviceProvider.handle(result);
        }
        return result;
    }
}
