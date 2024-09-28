package com.zjj.security.jwt.component;

import com.zjj.autoconfigure.component.jwt.JwtHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月26日 20:21
 */
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public JwtHelper jwtHelper(JwtProperties jwtProperties) {
        return new DefaultJwtHelper(jwtProperties);
    }
}
