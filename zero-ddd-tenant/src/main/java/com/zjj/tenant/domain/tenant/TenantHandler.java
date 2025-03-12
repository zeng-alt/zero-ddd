package com.zjj.tenant.domain.tenant;

import com.zjj.tenant.domain.tenant.cmd.*;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.cqrs.CommandHandler;
import org.springframework.stereotype.Service;

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
    private final TenantDataSourceRepository tenantDataSourceRepository;

    public void handler(StockInTenantCmd stockInTenantCmd) {
//        TenantAggregate tenant = this.tenantFactory.createTenant(stockInTenantCmd);
//        this.tenantRepository.save(tenant);

        this.tenantFactory.createTenant(stockInTenantCmd);
    }


    /**
     * <blockquote><pre>
     * mode = COLUMN   [db schema column] 数据库中不能有相同的db and schema and column
     * mode = SCHEMA   [db schema] 数据库中不能有相同的db and schema
     * mode = DATABASE [db] 数据库中不能有相同的db
     * </pre></blockquote>
     *
     * @param stockCmd 保存数据源
     */
    @CommandHandler
    public void handler(StockInTenantDataSourceCmd stockCmd) {
        this.tenantRepository
                .findById(stockCmd.tenantId())
//                .filter(t -> {
//                    if (stockCmd.schema() != null) {
//                        return tenantDataSourceRepository.findBySchema(stockCmd.schema()).isEmpty();
//                    }
//                    if (stockCmd.db() != null) {
//                        return tenantDataSourceRepository.findBySchema(stockCmd.db()).isEmpty();
//                    }
//                    return true;
//                })
                .map(t -> t.save(stockCmd))
                .getOrElseThrow(() -> new IllegalArgumentException("租户不存在"));
    }

//    public void handlerStockInTenantMenuCmd(@NonNull @lombok.NonNull Supplier<StockInTenantMenuCmd> stockCmd) {
//        this.handler(stockCmd.get());
//    }
//
//    public void handlerStockInTenantMenuCmd(@NonNull @lombok.NonNull UnaryOperator<StockInTenantMenuCmd.StockInTenantMenuCmdBuilder> stockCmd) {
//        this.handler(stockCmd.apply(StockInTenantMenuCmd.builder()).build());
//    }


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
                .map(TenantAggregate::disable)
                .map(tenantRepository::save)
                .getOrElseThrow(() -> new IllegalArgumentException(cmd.id() + " 租户不存在, 无法禁用"));
    }

    public void handler(EnableTenantCmd cmd) {
        this.tenantRepository
                .findById(cmd.id())
                .map(TenantAggregate::enable)
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
