package com.zjj.security.core.component.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月19日 21:37
 */
@Data
@Component
@ConfigurationProperties(prefix = "security")
public class LogoutProperties {
    private String logoutPath = "/logout";
    private HttpMethod method = HttpMethod.POST;
}
