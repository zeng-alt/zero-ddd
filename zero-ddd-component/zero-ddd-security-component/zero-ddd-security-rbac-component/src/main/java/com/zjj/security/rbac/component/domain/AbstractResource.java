package com.zjj.security.rbac.component.domain;

import lombok.Builder;
import lombok.Setter;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月05日 21:52
 */
@Setter
public class AbstractResource implements Resource {

    private String uri;
    private String method;

    @Override
    public String getUri() {
        return null;
    }

    @Override
    public String getMethod() {
        return null;
    }
}
