package com.zjj.autoconfigure.component.tenant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月23日 20:19
 */
@Data
@Component
@ConfigurationProperties(prefix = "tenant")
public class TenantProperties {
    private boolean enabled = false;
}
