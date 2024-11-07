package com.zjj.core.component.api;

import com.zjj.autoconfigure.component.core.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月07日 21:30
 */
@Getter
public class ExceptionResponse extends Response<Void> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String uri;
    private final String method;

    private ExceptionResponse(String message, String uri, String method) {
        super(FAIL, message);
        this.uri = uri;
        this.method = method;
    }

    public static ExceptionResponse of(String message, HttpServletRequest request) {
        return new ExceptionResponse(message, request.getRequestURI(), request.getMethod());
    }
}
