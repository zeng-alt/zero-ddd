package com.zjj.security.core.component.supper;

import com.zjj.autoconfigure.component.security.AuthenticationHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月19日 21:43
 */
public class DefaultLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication == null) {
            return;
        }
        String username = null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            username = userDetails.getUsername();
        }
        AuthenticationHelper.renderString(response, HttpStatus.OK.value(), username + " 登出成功");
    }
}
