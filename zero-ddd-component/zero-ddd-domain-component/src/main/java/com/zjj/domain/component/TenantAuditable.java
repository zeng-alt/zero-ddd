package com.zjj.domain.component;

import java.util.Optional;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月13日 10:31
 */
public interface TenantAuditable<T> {

    T getTenantBy();

    /**
     * Sets the user who created this entity.
     *
     * @param tenantBy the creating entity to set
     */
    void setTenantBy(T tenantBy);
}
