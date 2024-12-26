package com.zjj.exchange.common;

import com.zjj.autoconfigure.component.security.UserContextHolder;
import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.autoconfigure.component.tenant.TenantContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 接口请求拦截器
 */
@RequiredArgsConstructor
public class FeignClientInterceptor implements RequestInterceptor {


    private final String tenantToken;
    private final String fastToken;

    /**
     * 请求前会调用这个方法
     * @param requestTemplate 请求模板对象
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (!StringUtils.hasText(tenantToken)) {
            return;
        }

        requestTemplate.header(tenantToken, TenantContextHolder.getTenantId());

        if (!"anonymousUser".equals(UserContextHolder.getUsername())) {
            requestTemplate.header(fastToken, UserContextHolder.getUsername());
        }
    }
}
