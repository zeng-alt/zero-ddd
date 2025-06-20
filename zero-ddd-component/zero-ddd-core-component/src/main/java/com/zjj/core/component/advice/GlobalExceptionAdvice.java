package com.zjj.core.component.advice;

import com.zjj.autoconfigure.component.core.BaseException;
import com.zjj.autoconfigure.component.core.Response;
import com.zjj.core.component.api.ErrorResponseEntity;
import com.zjj.core.component.api.ExceptionResponse;
import com.zjj.i18n.component.BaseI18nException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionAdvice {

    private final MessageSourceAccessor messageSourceAccessor;

    @ExceptionHandler(BaseException.class)
    public ErrorResponse exception(BaseException e, HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(e.getCode());
        String requestURI = request.getRequestURI();
        log.error("{} 请求异常: ", requestURI, e);
        String message = messageSourceAccessor.getMessage(e.getMessage(), e.getMessage());
        ErrorResponseEntity errorResponseEntity = ErrorResponseEntity.of(HttpStatus.valueOf(e.getCode()), message);
        errorResponseEntity.setInstance(URI.create(request.getRequestURI()));
        errorResponseEntity.setTitle(message);
        return errorResponseEntity;
    }


    @ExceptionHandler(BaseI18nException.class)
    public ErrorResponse exception(BaseI18nException e, HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(e.getCode());
        String requestURI = request.getRequestURI();
        log.error("{} 请求异常: ", requestURI, e);
        ErrorResponseEntity errorResponseEntity = ErrorResponseEntity.of(HttpStatus.valueOf(e.getCode()), e.getMessage());
        errorResponseEntity.setInstance(URI.create(request.getRequestURI()));
        errorResponseEntity.setTitle(e.getMessage());
        return errorResponseEntity;
//        return ExceptionResponse.of(e.getCode(), e.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse exception(Exception e, HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        String requestURI = request.getRequestURI();
        log.error("{}: {} 请求未知异常:", request.getMethod(), requestURI, e);
        String message = messageSourceAccessor.getMessage("GlobalExceptionAdvice.exception.error", e.getMessage());
        ErrorResponseEntity errorResponseEntity = ErrorResponseEntity.of(HttpStatus.INTERNAL_SERVER_ERROR, message);
        errorResponseEntity.setInstance(URI.create(request.getRequestURI()));
        errorResponseEntity.setTitle(message);
        return errorResponseEntity;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse exception(IllegalArgumentException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("{}: {} 请求参数异常:", request.getMethod(), requestURI, e);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.of(HttpStatus.BAD_REQUEST, e.getMessage());
        errorResponse.setInstance(URI.create(request.getRequestURI()));
        errorResponse.setTitle(e.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse exception(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("{}: {} 请求未知运行异常:", request.getMethod(), requestURI, e);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        errorResponse.setInstance(URI.create(request.getRequestURI()));
        errorResponse.setTitle("请求未知运行异常");
        return errorResponse;
    }

//    @ExceptionHandler(NoResourceFoundException.class)
//    public Response<Void> exception(NoResourceFoundException e, HttpServletRequest request) {
//        log.error("", e);
//        return ExceptionResponse.of(404, messageSourceAccessor.getMessage(e.getMessage(), e.getMessage()), request);
//    }

}
