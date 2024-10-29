package com.zjj.security.tenant.component.spi;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月24日 20:28
 */
public interface TenantDetail extends UserDetails {

    String getTenantName();
}
