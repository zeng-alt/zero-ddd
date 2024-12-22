package com.zjj.tenant.infrastructure.db.jpa;

import com.zjj.graphql.component.supper.BaseRepository;
import com.zjj.tenant.domain.tenant.ITenant;
import com.zjj.tenant.domain.tenant.Tenant;
import io.vavr.control.Option;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 21:09
 */
@GraphQlRepository
public interface TenantDao extends BaseRepository<Tenant, Long> {

    Option<Tenant> findById(Long id);

    Tenant save(Tenant tenant);

    Option<Tenant> findByTenantKey(String tenantKey);

    List<Tenant> findAll();
}
