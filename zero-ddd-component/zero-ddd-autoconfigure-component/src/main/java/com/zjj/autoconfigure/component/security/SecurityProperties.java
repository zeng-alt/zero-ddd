package com.zjj.autoconfigure.component.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月25日 21:54
 */
@Data
@Component
@ConfigurationProperties(prefix = "security.context")
public class SecurityProperties {
    private Boolean enabledAccess = true;
    private Boolean enabledLoginHandler = false;
    private String abacPrefix = "/";
}
