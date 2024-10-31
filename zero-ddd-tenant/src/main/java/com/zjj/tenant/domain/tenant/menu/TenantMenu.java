package com.zjj.tenant.domain.tenant.menu;

import com.zjj.domain.component.BaseEntity;
import com.zjj.tenant.domain.menu.MenuResource;
import com.zjj.tenant.domain.tenant.Tenant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 15:50
 */
@Getter
@Setter
@Entity
@Table(name = "tenant_menu")
public class TenantMenu extends BaseEntity<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "menu_resource_id")
    private MenuResource menuResource;

}
