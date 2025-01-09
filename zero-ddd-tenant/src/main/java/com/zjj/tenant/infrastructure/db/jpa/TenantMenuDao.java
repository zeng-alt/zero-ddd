package com.zjj.tenant.infrastructure.db.jpa;

import com.zjj.graphql.component.supper.BaseRepository;
import com.zjj.tenant.domain.tenant.TenantMenu;
import com.zjj.tenant.infrastructure.db.entity.TenantMenuEntity;
import io.vavr.control.Option;
import org.springframework.graphql.data.GraphQlRepository;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 21:11
 */
@GraphQlRepository
public interface TenantMenuDao extends BaseRepository<TenantMenuEntity, Long> {

    Option<TenantMenuEntity> findById(Long id);

    TenantMenuEntity save(TenantMenuEntity tenantMenu);

    void saveAll(Iterable<TenantMenuEntity> tenantMenuList);
}
