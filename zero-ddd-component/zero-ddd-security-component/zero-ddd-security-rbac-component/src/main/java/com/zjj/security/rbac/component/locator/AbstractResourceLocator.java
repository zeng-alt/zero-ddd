package com.zjj.security.rbac.component.locator;

import com.zjj.autoconfigure.component.security.rbac.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月11日 21:52
 */
@Slf4j
public abstract class AbstractResourceLocator implements ResourceLocator {

    protected abstract List<Resource> list(@Nullable Object o);

    private Object getAuthorizationPrincipal(Authentication authentication) {
        return authentication.getPrincipal();
    }

    private boolean isNotAnonymous(Authentication authentication) {
        return authentication.isAuthenticated();
    }

    @Override
    public List<Resource> load(Authentication authentication) throws AuthenticationException {
        if (!isNotAnonymous(authentication)) {
            return new ArrayList<>();
        }
        return list(getAuthorizationPrincipal(authentication));
    }
}
