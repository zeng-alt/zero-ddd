package com.zjj.tenant.infrastructure.repository;

import com.zjj.tenant.infrastructure.db.jpa.TenantMenuDao;
import com.zjj.tenant.domain.tenant.menu.TenantMenu;
import com.zjj.tenant.domain.tenant.menu.TenantMenuRepository;
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

    public Void saveAll(Collection<TenantMenu> tenantMenuList) {
        tenantMenuDao.saveAll(tenantMenuList);
        return null;
    }
}
