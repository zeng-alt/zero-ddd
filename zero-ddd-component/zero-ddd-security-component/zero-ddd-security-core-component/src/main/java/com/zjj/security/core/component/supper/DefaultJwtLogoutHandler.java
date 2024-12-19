package com.zjj.security.core.component.supper;

import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月19日 21:28
 */
@RequiredArgsConstructor
public class DefaultJwtLogoutHandler implements LogoutHandler {

    private final JwtCacheManage jwtCacheManage;

    /**
     * Causes a logout to be completed. The method must complete successfully.
     *
     * @param request        the HTTP request
     * @param response       the HTTP response
     * @param authentication the current principal details
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication == null) {
            return;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            jwtCacheManage.remove(userDetails.getUsername());
        }
    }
}
