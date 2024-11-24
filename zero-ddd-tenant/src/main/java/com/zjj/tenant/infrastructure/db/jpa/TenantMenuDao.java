package com.zjj.tenant.infrastructure.db.jpa;

import com.zjj.graphql.component.supper.BaseRepository;
import com.zjj.tenant.domain.tenant.menu.TenantMenu;
import io.vavr.control.Option;
import org.springframework.graphql.data.GraphQlRepository;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 21:11
 */
@GraphQlRepository
public interface TenantMenuDao extends BaseRepository<TenantMenu, Long> {

    Option<TenantMenu> findById(Long id);

    TenantMenu save(TenantMenu tenantMenu);

    void saveAll(Iterable<TenantMenu> tenantMenuList);
}
