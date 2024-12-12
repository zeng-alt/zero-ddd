package com.zjj.security.rbac.component.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月29日 21:20
 */
public interface ResourceHandler {

    public boolean matcher(HttpServletRequest request);


    public Boolean handler(Authentication authentication, RequestAuthorizationContext object);
}
