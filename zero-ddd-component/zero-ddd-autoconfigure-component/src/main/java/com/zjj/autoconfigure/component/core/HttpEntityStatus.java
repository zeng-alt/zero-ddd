package com.zjj.autoconfigure.component.core;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月13日 21:43
 */
public class HttpEntityStatus<T> extends HttpEntity<T> {

    protected final Integer status;

    public HttpEntityStatus(@Nullable T body, @Nullable MultiValueMap<String, String> headers, Integer status) {
        super(body, headers);
        this.status = status;
    }

    /**
     * Return the HTTP status code of the response.
     * @return the HTTP status as an HttpStatus enum entry
     */
    public Integer getStatusCode() {
        return this.status;
    }
}
