package com.zjj.main.infrastructure.db.jpa.entity;

import com.zjj.core.component.api.Parent;
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
 * @crateTime 2024年11月15日 21:42
 */
@Getter
@Setter
@Entity
@Table(name = "main_resource")
public class MenuResource extends BaseEntity<Long> implements Parent<Long>, TenantAuditable<String> {

    @Id
    @GeneratedValue
    private Long id;


    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "permission_id")
    private Permission permission;

    @OneToMany(mappedBy = "resource", orphanRemoval = true)
    private Set<Expression> expressions = new LinkedHashSet<>();

    /**
     * 父菜单
     */
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private MenuResource parentMenu;

    /**
     * 子菜单
     */

    @OneToMany(mappedBy = "parentMenu")
    private Set<MenuResource> chileMenus = new LinkedHashSet<>();

    @TenantId
    @Nullable
    private String tenantBy;

    @Override
    public Long parent() {
        return parentMenu == null ? null : parentMenu.getId();
    }

    @Override
    public Long current() {
        return id;
    }
}
