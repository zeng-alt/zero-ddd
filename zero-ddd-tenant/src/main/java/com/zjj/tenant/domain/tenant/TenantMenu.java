package com.zjj.tenant.domain.tenant;

import com.zjj.tenant.domain.menu.MenuResource;
import com.zjj.tenant.domain.menu.MenuResourceAggregate;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Association;
import org.jmolecules.ddd.types.Identifier;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 15:50
 */
@Getter
@Setter
public class TenantMenu implements TenantMenuAggregate, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private TenantMenuId id;

    private String status = "0";


    private Association<Tenant, TenantId> tenant;


    private Association<MenuResource, MenuResource.MenuResourceId> menuResource;

    public TenantMenu() {}


    public TenantMenu(Association<MenuResource, MenuResource.MenuResourceId> menuResource) {
        this.menuResource = menuResource;
    }

    @Value(staticConstructor = "of")
    public static class TenantMenuId implements Identifier {
        Long id;
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
