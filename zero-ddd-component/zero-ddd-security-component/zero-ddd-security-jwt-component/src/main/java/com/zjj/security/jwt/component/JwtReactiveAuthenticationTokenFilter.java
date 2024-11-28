package com.zjj.security.jwt.component;

import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtHelper;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import org.springframework.web.server.WebFilter;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月26日 21:01
 */

public abstract class JwtReactiveAuthenticationTokenFilter implements WebFilter {

    protected final JwtHelper jwtHelper;
    protected final JwtCacheManage jwtCacheManage;
    protected final JwtProperties jwtProperties;

    protected JwtReactiveAuthenticationTokenFilter(JwtHelper jwtHelper, JwtCacheManage jwtCacheManage, JwtProperties jwtProperties) {
        this.jwtHelper = jwtHelper;
        this.jwtCacheManage = jwtCacheManage;
        this.jwtProperties = jwtProperties;
    }
}