package com.zjj.tenant.management.component.spi;

import com.zjj.autoconfigure.component.tenant.Tenant;
import com.zjj.exchange.tenant.client.RemoteTenantClient;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月27日 21:20
 */
@RequiredArgsConstructor
public class DefaultTenantSingleDataSourceProvider implements TenantSingleDataSourceProvider {

    private final RemoteTenantClient remoteTenantClient;

    @Override
    public Option<Tenant> findById(String id) {

        return remoteTenantClient.findById(id);
    }

}
