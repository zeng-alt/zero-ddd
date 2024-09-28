package com.zjj.autoconfigure.component.jwt;

import org.springframework.util.Assert;
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
