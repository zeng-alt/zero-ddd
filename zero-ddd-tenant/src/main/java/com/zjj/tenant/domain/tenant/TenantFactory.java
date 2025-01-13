package com.zjj.tenant.domain.tenant;

import com.zjj.bean.componenet.ApplicationContextHelper;
import com.zjj.bean.componenet.BeanHelper;
import com.zjj.tenant.domain.tenant.cmd.StockInTenantCmd;
import com.zjj.tenant.domain.tenant.event.CreateTenantEvent;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 11:49
 */
@Component
@RequiredArgsConstructor
public class TenantFactory {

    private final TenantRepository tenantRepository;

    public void createTenant(StockInTenantCmd stockInTenantCmd) {

        tenantRepository
                .findByTenantKey(stockInTenantCmd::tenantKey)
                .map(tenant -> {
                    throw new IllegalArgumentException(stockInTenantCmd.tenantKey() + " 租户已存在");
                })
                .orElse(() -> Option.of(stockInTenantCmd))
                .map(t -> BeanHelper.copyToObject(t, Tenant.class))
                .forEach(tenant -> ApplicationContextHelper.publishEvent(CreateTenantEvent.apply(tenant)));
//                .getOrElseThrow(() -> new IllegalArgumentException("租户转换失败"));

    }
}

