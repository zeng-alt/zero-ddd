package com.zjj.autoconfigure.component.security.rbac;

import lombok.Setter;
import org.springframework.http.HttpMethod;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月05日 21:52
 */
@Setter
public class AbstractResource implements Resource {

    private String uri;
    private HttpMethod method;

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public HttpMethod getMethod() {
        return method;
    }
}
