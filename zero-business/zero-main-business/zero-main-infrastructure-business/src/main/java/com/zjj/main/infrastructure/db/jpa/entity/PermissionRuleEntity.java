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
 * @crateTime 2025年06月09日 15:37
 */
@Getter
@Entity(name = "PermissionRule")
@Setter
@Table(name = "main_permission")
public class PermissionRuleEntity extends BaseEntity<Long> implements TenantAuditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resource_type", insertable = false, updatable = false)
    private String resourceType;

    private String code;

    private String name;

    @OneToMany(mappedBy = "permission")
    private Set<PolicyRuleEntity> rules = new LinkedHashSet<>();

    @TenantId
    @Nullable
    private String tenantBy;
}
