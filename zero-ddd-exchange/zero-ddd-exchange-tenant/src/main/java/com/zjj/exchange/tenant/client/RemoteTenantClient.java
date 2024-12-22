package com.zjj.exchange.tenant.client;

import com.zjj.exchange.tenant.factory.RemoteTenantFallbackFactory;
import com.zjj.exchange.tenant.sevice.RemoteTenantService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author zengJiaJun
 * @crateTime 2024年12月21日 22:24
 * @version 1.0
 */
@FeignClient(contextId = "remoteTenantService", value = "zero-ddd-tenant", fallbackFactory = RemoteTenantFallbackFactory.class, path = "/remote/tenant")
public interface RemoteTenantClient extends RemoteTenantService {
}
