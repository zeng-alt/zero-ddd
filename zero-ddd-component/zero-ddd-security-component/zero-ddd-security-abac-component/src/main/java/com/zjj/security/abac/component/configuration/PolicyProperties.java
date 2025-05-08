package com.zjj.security.abac.component.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月16日 21:33
 */
@Data
@Component
@ConfigurationProperties(prefix = "security.policy")
public class PolicyProperties {

    private String preFilePath = "default-pre-policy.json";

    private String postFilePath = "default-post-policy.json";
}
