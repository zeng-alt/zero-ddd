package com.zjj.tenant.domain.tenant;


import com.zjj.autoconfigure.component.core.BaseException;
import com.zjj.bean.componenet.BeanHelper;
import com.zjj.tenant.domain.tenant.cmd.StockInTenantDataSourceCmd;
import com.zjj.tenant.domain.tenant.cmd.StockInTenantMenuCmd;
import com.zjj.tenant.domain.tenant.event.*;
import lombok.Getter;
import lombok.Setter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Association;
import org.springframework.beans.BeanUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.zjj.bean.componenet.ApplicationContextHelper.publishEvent;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 21:23
 */
@Getter
@Setter
public class Tenant implements AggregateRoot<Tenant, TenantId>, TenantAggregate, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private TenantId id;

    /**
     * 租户编号
     */
    private String tenantKey;

    /**
     * 联系人
     */
    private String contactUserName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 统一社会信用代码
     */
    private String licenseNumber;

    /**
     * 地址
     */
    private String address;

    /**
     * 域名
     */
    private String domain;

    /**
     * 企业简介
     */
    private String intro;

    /**
     * 备注
     */
    private String remark;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 用户数量（-1不限制）
     */
    private Long accountCount;

    /**
     * 租户状态（0正常 1停用）
     */
    private String status = "1";


    private TenantDataSource tenantDataSource;


    private Set<Association<TenantMenu, TenantMenu.TenantMenuId>> tenantMenus = new LinkedHashSet<>();

    @Override
    public TenantAggregate save(StockInTenantDataSourceCmd sourceCmd) {
        if (this.tenantDataSource != null) {
            BeanUtils.copyProperties(sourceCmd, this.tenantDataSource);
        } else {
            tenantDataSource = BeanHelper.copyToObject(sourceCmd, TenantDataSource.class);
        }
        publishEvent(new CreateTenantDataSourceEvent(this.tenantKey, sourceCmd));
        return this;
    }

    @Override
    public TenantAggregate save(StockInTenantMenuCmd sourceCmd) {
        Set<Association<TenantMenu, TenantMenu.TenantMenuId>> set = new HashSet<>(sourceCmd.menuResources());

        for (Association<TenantMenu, TenantMenu.TenantMenuId> tenantMenu : tenantMenus) {
            if (set.contains(tenantMenu)) {
                set.remove(tenantMenu);
                set.add(tenantMenu);
            }
        }

        this.tenantMenus.clear();
        this.tenantMenus.addAll(set);

        publishEvent(
                UpdateTenantMenuEvent
                        .apply(
                                this,
                                this.tenantKey,
                                this.tenantMenus.stream().map(m -> m.getId().getId()).collect(Collectors.toSet())
                        )
        );
        return this;
    }

    @Override
    public TenantAggregate disable() {
        this.status = "1";
        publishEvent(DisableTenantEvent.apply(this, this.tenantKey));
        return this;
    }

    @Override
    public TenantAggregate enable() {
        this.status = "0";
        if (this.tenantDataSource == null) {
            throw new BaseException("数据源为空, 无法启用租户");
        }
        publishEvent(EnableTenantEvent.apply(this, this.id.getId(), this.tenantDataSource));
        return this;
    }

    @Override
    public TenantAggregate disableMenu(Long menuId) {
        for (Association<TenantMenu, TenantMenu.TenantMenuId> tenantMenu : this.tenantMenus) {
            if (tenantMenu.getId() != null && tenantMenu.pointsTo(TenantMenu.TenantMenuId.of(menuId))) {
                publishEvent(DisableTenantMenuEvent.apply(this, tenantMenu.getId().getId(), tenantKey));
                return this;
            }
        }


        throw new IllegalArgumentException(menuId + "没有相关的菜单资源");
    }

    @Override
    public TenantAggregate enableMenu(Long menuId) {
        for (Association<TenantMenu, TenantMenu.TenantMenuId> tenantMenu : this.tenantMenus) {
            if (tenantMenu.getId() != null && menuId.equals(tenantMenu.getId().getId())) {
                publishEvent(DisableTenantMenuEvent.apply(this, tenantMenu.getId().getId(), tenantKey));
                return this;
            }
        }
        throw new IllegalArgumentException(menuId + "没有相关的菜单资源");
    }
}
