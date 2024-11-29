package com.zjj.autoconfigure.component.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月28日 21:42
 */
@Data
@Component
@ConfigurationProperties(prefix = "security.filter")
public class WhiteListProperties {

    private Set<String> ignoreUrl = new HashSet<>();
}
