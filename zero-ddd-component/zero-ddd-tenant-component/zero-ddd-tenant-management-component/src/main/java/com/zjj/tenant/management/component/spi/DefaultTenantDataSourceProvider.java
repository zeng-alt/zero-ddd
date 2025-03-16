package com.zjj.tenant.management.component.spi;

import com.zjj.autoconfigure.component.tenant.Tenant;
import com.zjj.exchange.tenant.client.RemoteTenantClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;

import java.util.Collection;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月27日 21:00
 */
@RequiredArgsConstructor
public class DefaultTenantDataSourceProvider implements TenantDataSourceProvider {

    private final RemoteTenantClient remoteTenantClient;

    @Override
    public Collection<Tenant> findAll() {
        return remoteTenantClient.findAll();
    }
}
