package com.zjj.tenant.domain.tenant.menu;

import com.zjj.domain.component.BaseEntity;
import com.zjj.tenant.domain.menu.MenuResource;
import com.zjj.tenant.domain.tenant.Tenant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    private String status = "0";

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "menu_resource_id")
    private MenuResource menuResource;

    public TenantMenu() {}


    public TenantMenu(MenuResource menuResource) {
        this.menuResource = menuResource;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TenantMenu that = (TenantMenu) o;

        return new EqualsBuilder().append(tenant, that.tenant).append(menuResource, that.menuResource).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(tenant).append(menuResource).toHashCode();
    }

    public TenantMenu disable() {
        status = "1";
        return this;
    }

    public TenantMenu enable() {
        status = "0";
        return this;
    }
}
