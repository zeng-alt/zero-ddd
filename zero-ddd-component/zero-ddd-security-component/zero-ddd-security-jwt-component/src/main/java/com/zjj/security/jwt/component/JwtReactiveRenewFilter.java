package com.zjj.security.jwt.component;

import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import org.springframework.web.server.WebFilter;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月19日 21:07
 */
public abstract class JwtReactiveRenewFilter implements WebFilter {

    protected final JwtCacheManage jwtCacheManage;

    protected JwtReactiveRenewFilter(JwtCacheManage jwtCacheManage) {
        this.jwtCacheManage = jwtCacheManage;
    }
}
