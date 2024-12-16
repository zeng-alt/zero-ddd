package com.zjj.security.abac.component.advice;

import com.zjj.autoconfigure.component.core.Response;
import com.zjj.core.component.api.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月16日 21:32
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AbacExceptionAdvice {

    @ExceptionHandler(AuthorizationDeniedException.class)
    public Response<Void> exception(AuthorizationDeniedException e, HttpServletRequest request) {
        log.warn("{}: {} 请求被拒绝: {}", request.getMethod(), request.getRequestURI(), e.getMessage());
        return ExceptionResponse.of(HttpStatus.FORBIDDEN.value(), e.getMessage(), request);
    }
}
