package com.zjj.security.tenant.component.supper;

import java.io.IOException;

import com.zjj.tenant.component.spi.DynamicSourceManage;
import com.zjj.tenant.component.spi.TenantContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 在登录认证时进行区别租户
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月23日 20:39
 */
@Slf4j
@RequiredArgsConstructor
public class TenantFilter extends OncePerRequestFilter {

    private final TenantContextHolder tenantManage;
    private final DynamicSourceManage dynamicSourceManage;
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login/**", "POST");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!DEFAULT_ANT_PATH_REQUEST_MATCHER.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String tenant = request.getHeader("tenant");
        if (tenant == null) {
            dynamicSourceManage.switchPrimaryDataSource();
            log.debug("切换到主数据源");
            filterChain.doFilter(request, response);
            return;
        }

        boolean hasAccess = isUserAllowed(tenant);
        if (hasAccess) {
            dynamicSourceManage.switchDataSource(tenant);
            log.debug("切换数据源为：{}", tenant);
            filterChain.doFilter(request, response);
            return;
        }
        throw new AccessDeniedException("tenant Access denied");
    }

    private boolean isUserAllowed(String tenant) {
        return tenantManage.getTenants().contains(tenant);
    }

}