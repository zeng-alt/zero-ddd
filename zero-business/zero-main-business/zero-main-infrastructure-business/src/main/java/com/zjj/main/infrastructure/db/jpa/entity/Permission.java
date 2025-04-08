package com.zjj.main.infrastructure.db.jpa.entity;

import com.zjj.domain.component.BaseEntity;
import com.zjj.domain.component.TenantAuditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.TenantId;
import org.springframework.lang.Nullable;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月15日 21:41
 */
@Getter
@Setter
@Entity
@Table(name = "main_permission")
public class Permission extends BaseEntity<Long> implements TenantAuditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "permission", orphanRemoval = true)
    private Set<RolePermission> rolePermissions = new LinkedHashSet<>();

    @OneToOne
    @JoinColumn(name = "resource_id")
    private Resource resource;

    private String code;

    @TenantId
    @Nullable
    private String tenantBy;
}
