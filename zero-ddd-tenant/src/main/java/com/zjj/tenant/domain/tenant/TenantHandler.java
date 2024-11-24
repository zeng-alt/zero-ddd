package com.zjj.tenant.domain.tenant;

import com.zjj.tenant.domain.tenant.cmd.*;
import com.zjj.tenant.domain.tenant.menu.TenantMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 21:29
 */
@Service
@RequiredArgsConstructor
public class TenantHandler {

    private final TenantRepository tenantRepository;
    private final TenantMenuRepository tenantMenuRepository;
    private final TenantFactory tenantFactory;

    public void handler(StockInTenantCmd stockInTenantCmd) {
        ITenant tenant = this.tenantFactory.createTenant(stockInTenantCmd);
        this.tenantRepository.save(tenant);
    }


    public void handler(StockInTenantDataSourceCmd stockCmd) {
        this.tenantRepository
                .findById(stockCmd.tenantId())
                .map(t -> t.save(stockCmd))
                .map(tenantRepository::save)
                .getOrElseThrow(() -> new IllegalArgumentException("租户不存在"));
    }

    public void handlerStockInTenantMenuCmd(@NonNull @lombok.NonNull Supplier<StockInTenantMenuCmd> stockCmd) {
        this.handler(stockCmd.get());
    }

    public void handlerStockInTenantMenuCmd(@NonNull @lombok.NonNull UnaryOperator<StockInTenantMenuCmd.StockInTenantMenuCmdBuilder> stockCmd) {
        this.handler(stockCmd.apply(StockInTenantMenuCmd.builder()).build());
    }


    public void handler(StockInTenantMenuCmd cmd) {

        this.tenantRepository
                .findById(cmd.tenantId())
                .map(t -> t.save(cmd))
                .map(tenantRepository::save)
                .getOrElseThrow(() -> new IllegalArgumentException("租户不存在"));

    }

    public void handler(DisableTenantCmd cmd) {
        this.tenantRepository
                .findById(cmd.id())
                .map(ITenant::disable)
                .map(tenantRepository::save)
                .getOrElseThrow(() -> new IllegalArgumentException(cmd.id() + " 租户不存在, 无法禁用"));
    }

    public void handler(EnableTenantCmd cmd) {
        this.tenantRepository
                .findById(cmd.id())
                .map(ITenant::enable)
                .map(tenantRepository::save)
                .getOrElseThrow(() -> new IllegalArgumentException(cmd.id() + " 租户不存在, 无法开启"));
    }

    public void handler(DisableTenantMenuCmd cmd) {
        this.tenantRepository
                .findById(cmd.tenantId())
                .map(t -> t.disableMenu(cmd.menuId()))
                .map(tenantRepository::save)
                .getOrElseThrow(() -> new IllegalArgumentException(cmd.tenantId() + " 租户不存在, 租户无法禁用"));
    }

    public void handler(EnableTenantMenuCmd cmd) {
        this.tenantRepository
                .findById(cmd.tenantId())
                .map(t -> t.enableMenu(cmd.menuId()))
                .map(tenantRepository::save)
                .getOrElseThrow(() -> new IllegalArgumentException(cmd.tenantId() + " 租户不存在, 租户无法开启"));
    }
}
