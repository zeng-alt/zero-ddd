package com.zjj.tenant.Infrastructure.jpa;

import com.zjj.graphql.component.supper.BaseRepository;
import com.zjj.tenant.domain.tenant.Tenant;
import io.vavr.control.Option;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.Optional;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 21:09
 */
@GraphQlRepository
public interface TenantDao extends BaseRepository<Tenant, Long> {

    Option<Tenant> findByTenantKey(String tenantKey);
}
