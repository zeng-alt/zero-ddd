package com.zjj.tenant.infrastructure.db.entity;

import com.zjj.domain.component.BaseEntity;
import com.zjj.graphql.component.annotations.MutationEntity;
import com.zjj.tenant.infrastructure.db.mutation.TenantEntitySaveHandler;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.lang.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年01月07日 21:20
 */
@Entity(name = "Tenant")
@Table(name = "tenant")
@Getter
@Setter
@MutationEntity(saveHandlers = {TenantEntitySaveHandler.class})
public class TenantEntity  extends BaseEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 租户编号
     */
    @NotEmpty(message = "租户编号不能为空")
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
    @NotEmpty(message = "企业名称不能为空")
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

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "tenant_data_source_id")
    @NotNull(message = "数据源不能为空")
    private TenantDataSourceEntity tenantDataSource;

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL)
    private Set<TenantMenuEntity> tenantMenus = new LinkedHashSet<>();
}
