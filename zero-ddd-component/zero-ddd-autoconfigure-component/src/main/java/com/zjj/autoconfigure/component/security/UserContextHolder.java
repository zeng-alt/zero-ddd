package com.zjj.autoconfigure.component.security;

import com.zjj.autoconfigure.component.tenant.TenantDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:57
 */
public class UserContextHolder {

    private UserContextHolder() {}

    public static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        return authentication.getName();
    }

    public static UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        return (UserDetails) authentication.getPrincipal();
    }


    public static String getTenant() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof TenantDetail tenantDetail) {
            return tenantDetail.getTenantName();
        }

        return null;
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static void setAuthentication(Authentication authentication) {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        } else {
            context.setAuthentication(authentication);
        }
    }

    public static SecurityUser getSecurityUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof SecurityUser securityUser) {
            return securityUser;
        }

        return null;
    }


    public static void set(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static void clear() {
        SecurityContextHolder.clearContext();
    }
}
