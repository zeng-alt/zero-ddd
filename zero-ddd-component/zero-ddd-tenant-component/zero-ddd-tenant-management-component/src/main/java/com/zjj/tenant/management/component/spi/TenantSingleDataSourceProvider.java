package com.zjj.tenant.management.component.spi;

import com.zjj.autoconfigure.component.tenant.Tenant;
import io.vavr.control.Option;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月27日 21:18
 */
public interface TenantSingleDataSourceProvider {

    public Option<Tenant> findById(String id);
}
