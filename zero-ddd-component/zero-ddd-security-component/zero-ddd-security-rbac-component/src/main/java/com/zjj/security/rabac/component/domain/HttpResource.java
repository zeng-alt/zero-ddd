package com.zjj.security.rabac.component.domain;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月09日 16:26
 */
public interface HttpResource {

    public String getUri();

    public String getMethod();


    default boolean compareTo(HttpServletRequest request) {
        return AntPathRequestMatcher.antMatcher(HttpMethod.valueOf(getMethod()), getUri()).matcher(request).isMatch();
    }

}
