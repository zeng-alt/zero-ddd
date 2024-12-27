package com.zjj.tenant.management.component.spi;

import com.zjj.autoconfigure.component.tenant.Tenant;

import java.util.Collection;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月27日 21:07
 */
public interface TenantDataSourceProvider {

    Collection<Tenant> findAll();
}
