package com.zjj.security.jwt.component;

import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.ReactiveJwtCacheManage;
import org.springframework.web.server.WebFilter;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月19日 21:07
 */
public abstract class JwtReactiveRenewFilter implements WebFilter {

    protected final ReactiveJwtCacheManage jwtCacheManage;

    protected JwtReactiveRenewFilter(ReactiveJwtCacheManage jwtCacheManage) {
        this.jwtCacheManage = jwtCacheManage;
    }
}
