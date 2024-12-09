package com.zjj.security.rbac.component.parse;

import com.zjj.autoconfigure.component.security.rbac.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月06日 21:43
 */
public interface ResourceLocator {

    // TODO
    Authentication authenticate(Resource resource) throws AuthenticationException;

    boolean supports(Class<?> resource);
}
