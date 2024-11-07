package com.zjj.core.component.advice;

import com.zjj.autoconfigure.component.core.BaseException;
import com.zjj.autoconfigure.component.core.Response;
import com.zjj.core.component.api.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionAdvice {

    private final MessageSourceAccessor messageSourceAccessor;


    @ExceptionHandler(Exception.class)
    public Response<Void> exception(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("{}: {} 请求未知异常:", request.getMethod(), requestURI, e);
        return ExceptionResponse.of(messageSourceAccessor.getMessage("GlobalExceptionAdvice.exception.error", e.getMessage()), request);
    }

    @ExceptionHandler(BaseException.class)
    public Response<Void> exception(BaseException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("{} 请求异常: ", requestURI, e);
        return ExceptionResponse.of(e.getMessage(), request);
    }

}
