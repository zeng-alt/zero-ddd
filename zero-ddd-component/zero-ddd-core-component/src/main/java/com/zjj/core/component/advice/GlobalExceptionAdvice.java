package com.zjj.core.component.advice;

import com.zjj.autoconfigure.component.core.BaseException;
import com.zjj.autoconfigure.component.core.Response;
import com.zjj.core.component.api.ExceptionResponse;
import com.zjj.i18n.component.BaseI18nException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    @ExceptionHandler(BaseException.class)
    public Response<Void> exception(BaseException e, HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(e.getCode());
        String requestURI = request.getRequestURI();
        log.error("{} 请求异常: ", requestURI, e);
        return ExceptionResponse.of(e.getCode(), messageSourceAccessor.getMessage(e.getMessage(), e.getMessage()), request);
    }


    @ExceptionHandler(BaseI18nException.class)
    public Response<Void> exception(BaseI18nException e, HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(e.getCode());
        String requestURI = request.getRequestURI();
        log.error("{} 请求异常: ", requestURI, e);
        return ExceptionResponse.of(e.getCode(), e.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public Response<Void> exception(Exception e, HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        String requestURI = request.getRequestURI();
        log.error("{}: {} 请求未知异常:", request.getMethod(), requestURI, e);
        return ExceptionResponse.of(messageSourceAccessor.getMessage("GlobalExceptionAdvice.exception.error", e.getMessage()), request);
    }

    @ExceptionHandler(RuntimeException.class)
    public Response<Void> exception(RuntimeException e, HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        String requestURI = request.getRequestURI();
        log.error("{}: {} 请求未知运行是异常:", request.getMethod(), requestURI, e);
        return ExceptionResponse.of(messageSourceAccessor.getMessage("GlobalExceptionAdvice.exception.error", e.getMessage()), request);
    }

//    @ExceptionHandler(NoResourceFoundException.class)
//    public Response<Void> exception(NoResourceFoundException e, HttpServletRequest request) {
//        log.error("", e);
//        return ExceptionResponse.of(404, messageSourceAccessor.getMessage(e.getMessage(), e.getMessage()), request);
//    }

}
