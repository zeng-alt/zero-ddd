package com.zjj.security.tenant.component.supper.web;

import com.zjj.autoconfigure.component.tenant.TenantContextHolder;
import com.zjj.autoconfigure.component.tenant.TenantDetail;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:45
 */
@Slf4j
@RequiredArgsConstructor
public class TenantWitchDataSourceFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if ("anonymousUser".equals(principal)) {
                // no tenant
            } else if (principal instanceof TenantDetail tenantDetail) {
                TenantContextHolder.setTenantId(tenantDetail.getTenantName());
                TenantContextHolder.setDatabase(tenantDetail.getDatabase());
                TenantContextHolder.setSchema(tenantDetail.getSchema());
            } else {
                log.error("无法转成TenantDetail 查看UserDetailService实现类");
                throw new ClassCastException("无法转成TenantDetail 查看UserDetailService实现类");
            }
        } else {
            // 在用户登录时
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContextHolder.clear();
        }
    }
}
