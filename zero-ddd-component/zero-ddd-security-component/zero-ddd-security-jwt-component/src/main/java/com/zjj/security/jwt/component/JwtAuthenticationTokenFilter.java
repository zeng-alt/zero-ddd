package com.zjj.security.jwt.component;

import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtHelper;
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
