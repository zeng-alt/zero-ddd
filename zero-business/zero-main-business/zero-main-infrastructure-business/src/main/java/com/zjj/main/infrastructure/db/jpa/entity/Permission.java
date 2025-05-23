package com.zjj.main.infrastructure.db.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Entity
@Setter
@Table(name = "main_permission")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "resource_type", discriminatorType = DiscriminatorType.STRING)
//@Table(name = "main_permission")
public class Permission extends BaseEntity<Long> implements TenantAuditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "permission", orphanRemoval = true)
    private Set<RolePermission> rolePermissions = new LinkedHashSet<>();
//
//    @OneToOne
//    @JoinColumn(name = "resource_id")
//    private Resource resource;

    @Column(name = "resource_type", insertable = false, updatable = false)
    private String resourceType;

    private String code;

    private String name;

//    @OneToMany(mappedBy = "resource")
//    private Set<PolicyRuleEntity> rules = new LinkedHashSet<>();

    @OneToMany(mappedBy = "permission")
    private Set<PolicyRuleEntity> rules = new LinkedHashSet<>();

    @TenantId
    @Nullable
    private String tenantBy;

    @JsonIgnore
    @Transient
    public String getKey() {
        throw new UnsupportedOperationException();
    }

    @JsonIgnore
    @Transient
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }
}
