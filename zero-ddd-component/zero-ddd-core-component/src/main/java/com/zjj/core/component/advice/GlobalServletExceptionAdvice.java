package com.zjj.core.component.advice;

import com.zjj.autoconfigure.component.core.BaseException;
import com.zjj.autoconfigure.component.core.Response;
import com.zjj.core.component.api.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月31日 21:20
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalServletExceptionAdvice {

    @ExceptionHandler(NoResourceFoundException.class)
    public Response<Void> exception(NoResourceFoundException e, HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        String requestURI = request.getRequestURI();
        log.error("{} 请求未知的资源: ", requestURI, e);
        return ExceptionResponse.of(HttpServletResponse.SC_NOT_FOUND, e.getMessage(), request);
    }
}
