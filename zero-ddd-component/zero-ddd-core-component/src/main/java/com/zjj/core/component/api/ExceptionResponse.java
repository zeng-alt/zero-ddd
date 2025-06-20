package com.zjj.core.component.api;

import com.zjj.autoconfigure.component.core.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.web.servlet.function.ServerRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月07日 21:30
 */
@Deprecated
@Getter
public class ExceptionResponse extends Response<Void> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String uri;
    private final String method;

    private ExceptionResponse(int code, String message, String uri, String method) {
        super(code, message);
        this.uri = uri;
        this.method = method;
    }

    public static ExceptionResponse of(String message, HttpServletRequest request) {
        return new ExceptionResponse(FAIL_CODE, message, request.getRequestURI(), request.getMethod());
    }

    public static ExceptionResponse of(String message, String uri, String method) {
        return new ExceptionResponse(FAIL_CODE, message, uri, method);
    }

    public static ExceptionResponse of(int code, String message, HttpServletRequest request) {
        return new ExceptionResponse(code, message, request.getRequestURI(), request.getMethod());
    }

    @Override
    public ExceptionResponse code(int code) {
        return new ExceptionResponse(code, getMessage(), getUri(), getMethod());
    }
}
