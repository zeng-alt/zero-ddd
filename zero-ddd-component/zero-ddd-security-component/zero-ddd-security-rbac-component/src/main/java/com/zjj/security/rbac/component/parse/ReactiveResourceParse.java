package com.zjj.security.rbac.component.parse;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月29日 21:16
 */
public interface ReactiveResourceParse {

    boolean isMatch(ServerHttpRequest request);


}
