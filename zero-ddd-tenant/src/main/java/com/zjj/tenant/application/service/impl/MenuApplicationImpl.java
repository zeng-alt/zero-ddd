package com.zjj.tenant.application.service.impl;

import com.zjj.tenant.application.service.MenuApplication;
import com.zjj.tenant.domain.menu.MenuResource;
import com.zjj.tenant.domain.menu.MenuResourceRepository;
import com.zjj.tenant.domain.tenant.TenantHandler;
import com.zjj.tenant.domain.tenant.cmd.StockInTenantMenuCmd;
import com.zjj.tenant.domain.tenant.TenantMenu;
import com.zjj.tenant.application.form.TenantMenuForm;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.jmolecules.ddd.types.Association;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月04日 21:13
 */
@Service
@RequiredArgsConstructor
public class MenuApplicationImpl implements MenuApplication {

    private final MenuResourceRepository menuResourceRepository;
    private final TenantHandler tenantHandler;

    @Transactional(rollbackFor = Exception.class)
    public void updateTenantMenu(TenantMenuForm tenantMenuFrom) {
        menuResourceRepository
                .findAllById(tenantMenuFrom.menuIds())
                .map(m -> new TenantMenu(Association.forAggregate((MenuResource) m)))
                .transform(list -> Option.of(new StockInTenantMenuCmd(tenantMenuFrom.tenantId(), list.toJavaList().stream().map(Association::forAggregate).toList())))
                .peek(tenantHandler::handler);
    }

}
