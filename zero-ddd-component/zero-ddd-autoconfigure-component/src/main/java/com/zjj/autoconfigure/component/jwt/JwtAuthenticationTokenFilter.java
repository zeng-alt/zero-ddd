package com.zjj.autoconfigure.component.jwt;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月26日 16:33
 */
public abstract class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    protected final JwtHelper jwtHelper;
    protected final JwtCacheManage jwtCacheManage;

    protected JwtAuthenticationTokenFilter(JwtHelper jwtHelper, JwtCacheManage jwtCacheManage) {
        this.jwtHelper = jwtHelper;
        this.jwtCacheManage = jwtCacheManage;
    }

}
