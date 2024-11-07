package com.zjj.tenant.domain.tenant;


import com.zjj.autoconfigure.component.core.BaseException;
import com.zjj.bean.componenet.BeanHelper;
import com.zjj.domain.component.BaseAggregate;
import com.zjj.tenant.domain.tenant.cmd.StockInTenantDataSourceCmd;
import com.zjj.tenant.domain.tenant.cmd.StockInTenantMenuCmd;
import com.zjj.tenant.domain.tenant.event.CreateTenantDataSourceEvent;
import com.zjj.tenant.domain.tenant.event.DisableTenantEvent;
import com.zjj.tenant.domain.tenant.event.EnableTenantEvent;
import com.zjj.tenant.domain.tenant.event.UpdateTenantMenuEvent;
import com.zjj.tenant.domain.tenant.menu.TenantMenu;
import com.zjj.tenant.domain.tenant.source.TenantDataSource;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 21:23
 */
@Getter
@Setter
@Entity
@Table(name = "tenant")
public class Tenant extends BaseAggregate<Long> implements ITenant, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Nullable
    @GeneratedValue
    private Long id;

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

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "tenant_data_source_id")
    private TenantDataSource tenantDataSource;

    @OneToMany(mappedBy = "tenant", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<TenantMenu> tenantMenus = new LinkedHashSet<>();

    @Override
    public ITenant save(StockInTenantDataSourceCmd sourceCmd) {
        if (this.tenantDataSource != null) {
            BeanUtils.copyProperties(sourceCmd, this.tenantDataSource);
        } else {
            tenantDataSource = BeanHelper.copyToObject(sourceCmd, TenantDataSource.class);
        }
        publishEvent(new CreateTenantDataSourceEvent(this, this.tenantKey, sourceCmd));
        return this;
    }

    @Override
    public ITenant save(StockInTenantMenuCmd sourceCmd) {
        Set<TenantMenu> set = sourceCmd.menuResources().stream().map(t -> {
            t.setTenant(this);
            return t;
        }).collect(Collectors.toSet());

        for (TenantMenu tenantMenu : tenantMenus) {
            if (set.contains(tenantMenu)) {
                set.remove(tenantMenu);
                set.add(tenantMenu);
            }
        }

        this.tenantMenus.clear();
        this.tenantMenus.addAll(set);

        this.publishEvent(
                UpdateTenantMenuEvent
                        .apply(
                                this,
                                this.tenantKey,
                                this.tenantMenus.stream().map(m -> m.getMenuResource().getId()).collect(Collectors.toSet())
                        )
        );
        return this;
    }

    @Override
    public ITenant disable() {
        this.status = "1";
        this.publishEvent(DisableTenantEvent.apply(this, this.tenantKey));
        return this;
    }

    @Override
    public ITenant enable() {
        this.status = "0";
        if (this.tenantDataSource == null) {
            throw new BaseException("数据源为空, 无法启用租户");
        }
        this.publishEvent(EnableTenantEvent.apply(this, this.id, this.tenantDataSource));
        return this;
    }
}
