package com.zjj.security.tenant.component.supper;

import com.zjj.autoconfigure.component.l2cache.L2CacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.tenant.component.spi.TenantContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月25日 15:34
 */
@RequiredArgsConstructor
public class TenantJwtL2CacheManage implements JwtCacheManage {

    private final L2CacheManage l2CacheManage;
    private final TenantContextHolder tenantContextHolder;

    @Override
    public UserDetails get(String id) {
        String tenant = tenantContextHolder.getCurrentTenant();
        return l2CacheManage.getL2Cache(tenant + ":jwt").get(id, UserDetails.class);
    }

    @Override
    public <T> T get(String id, Class<T> tClass) {
        String tenant = tenantContextHolder.getCurrentTenant();
        return l2CacheManage.getL2Cache(tenant + ":jwt").get(id, tClass);
    }

    @Override
    public void put(String id, UserDetails userDetails) {
        String tenant = tenantContextHolder.getCurrentTenant();
        l2CacheManage.getL2Cache(tenant + ":jwt").put(id, userDetails);
    }
}
