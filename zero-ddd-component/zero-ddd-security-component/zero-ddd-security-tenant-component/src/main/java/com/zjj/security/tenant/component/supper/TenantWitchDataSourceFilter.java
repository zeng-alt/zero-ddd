package com.zjj.security.tenant.component.supper;

import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import com.zjj.security.jwt.component.supper.DefaultJwtRenewFilter;
import com.zjj.security.tenant.component.spi.TenantDetail;
import com.zjj.tenant.component.spi.DynamicSourceManage;
import com.zjj.tenant.component.spi.TenantContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月25日 20:57
 */
@Slf4j
@RequiredArgsConstructor
public class TenantWitchDataSourceFilter extends OncePerRequestFilter {

    private final DynamicSourceManage dynamicSourceManage;
    private final TenantContextHolder tenantContextHolder;
    private final JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader(jwtProperties.getTokenHeader());
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        Object attribute = request.getAttribute(DefaultJwtRenewFilter.RENEW_KEY);
        if (Objects.isNull(attribute)) {
            log.error("没有对应的JwtDetail");
            filterChain.doFilter(request, response);
            return;
        }

        if (attribute instanceof TenantDetail tenantDetail) {
//            TenantContextHolder.setTenantId(tenantDetail.getTenantName());
            dynamicSourceManage.switchDataSource(tenantDetail.getTenantName());
            tenantContextHolder.setCurrentTenant(tenantDetail.getTenantName());
        } else {
            log.error("无法转成TenantDetail 查看UserDetailService实现类");
            throw new ClassCastException("无法转成TenantDetail 查看UserDetailService实现类");
        }
    }
}
