package com.zjj.tenant.infrastructure.repository;

import com.zjj.domain.component.DomainBeanHelper;
import com.zjj.tenant.domain.tenant.TenantMenuAggregate;
import com.zjj.tenant.infrastructure.db.entity.TenantMenuEntity;
import com.zjj.tenant.infrastructure.db.jpa.TenantMenuDao;
import com.zjj.tenant.domain.tenant.TenantMenu;
import com.zjj.tenant.domain.tenant.TenantMenuRepository;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月04日 13:43
 */
@Service
@RequiredArgsConstructor
public class TenantMenuRepositoryImpl implements TenantMenuRepository {

    private final TenantMenuDao tenantMenuDao;

    @Override
    public Option<TenantMenuAggregate> findById(Long id) {
        return tenantMenuDao.findById(id).map(t -> DomainBeanHelper.copyToDomain(t, TenantMenu.class, TenantMenu.TenantMenuId.class));
    }

    public Void saveAll(Collection<TenantMenuAggregate> tenantMenuList) {
        tenantMenuDao.saveAll(tenantMenuList.stream().map(t -> DomainBeanHelper.copyToDomain(t, TenantMenuEntity.class)).toList());
        return null;
    }

    @Override
    public TenantMenuAggregate save(TenantMenuAggregate tenantMenu) {

        if (tenantMenu instanceof TenantMenu t) {
            TenantMenuEntity entity = tenantMenuDao.save(DomainBeanHelper.copyToDomain(t, TenantMenuEntity.class));
            return DomainBeanHelper.copyToDomain(entity, TenantMenu.class, TenantMenu.TenantMenuId.class);
        }


        throw new IllegalArgumentException("TenantMenuAggregate is not instance of TenantMenu");
    }
}
