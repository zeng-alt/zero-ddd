package com.zjj.security.rbac.component.parse;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月29日 21:15
 */
public interface ResourceParse {

    boolean isMatch(HttpServletRequest httpServletRequest);

}
