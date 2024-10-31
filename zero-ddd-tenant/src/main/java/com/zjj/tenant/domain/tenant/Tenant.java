package com.zjj.tenant.domain.tenant;



import com.zjj.domain.component.BaseAggregate;
import com.zjj.tenant.domain.tenant.menu.TenantMenu;
import com.zjj.tenant.domain.tenant.source.TenantDataSource;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;


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
    private String status;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "tenant_data_source_id")
    private TenantDataSource tenantDataSource;

    @OneToMany(mappedBy = "tenant", orphanRemoval = true)
    private Set<TenantMenu> tenantMenus = new LinkedHashSet<>();

}
