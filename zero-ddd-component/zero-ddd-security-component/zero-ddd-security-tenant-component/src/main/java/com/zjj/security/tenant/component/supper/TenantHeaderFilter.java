package com.zjj.security.tenant.component.supper;


import com.zjj.autoconfigure.component.tenant.TenantContextHolder;
import com.zjj.autoconfigure.component.tenant.TenantService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 在登录认证时进行区别租户
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月23日 20:39
 */
@Slf4j
@RequiredArgsConstructor
public class TenantHeaderFilter extends OncePerRequestFilter {


    private final TenantService tenantService;
    private final String tenantToken;
    @Setter
    private String master;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tenant = request.getHeader(tenantToken);
        try {
            if (tenant == null) {
                TenantContextHolder.switchTenant(master);
                log.debug("切换到主数据源");
                filterChain.doFilter(request, response);
                return;
            }

            boolean hasAccess = isUserAllowed(tenant);
            if (!hasAccess) {
                throw new AccessDeniedException("tenant Access denied");
            }
            TenantContextHolder.switchTenant(tenant);
            log.debug("切换数据源为：{}", tenant);
            filterChain.doFilter(request, response);
        } finally {
            TenantContextHolder.clear();
        }
    }

    private boolean isUserAllowed(String tenant) {
        if (tenantService == null) {
            return true;
        }
        return tenantService.getTenants().contains(tenant);
    }

}