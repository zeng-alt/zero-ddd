package com.zjj.security.core.component.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月29日 21:57
 */
@Data
@Component
@ConfigurationProperties(prefix = "security.username-login")
public class UsernameLoginProperties {
    private Boolean enabled = false;
    private String usernameParameter = "username";
    private String passwordParameter = "password";
    private String loginPath = "/login/username";
}
