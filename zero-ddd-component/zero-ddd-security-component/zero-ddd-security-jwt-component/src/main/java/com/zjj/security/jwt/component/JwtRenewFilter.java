package com.zjj.security.jwt.component;

import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月26日 16:35
 */
public abstract class JwtRenewFilter extends OncePerRequestFilter {


    protected final JwtCacheManage jwtCacheManage;

    protected JwtRenewFilter(JwtCacheManage jwtCacheManage) {
        this.jwtCacheManage = jwtCacheManage;
    }
}
