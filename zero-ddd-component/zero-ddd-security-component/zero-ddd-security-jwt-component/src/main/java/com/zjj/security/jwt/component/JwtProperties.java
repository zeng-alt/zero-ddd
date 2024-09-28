package com.zjj.security.jwt.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月26日 20:50
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private static final String CLAIM_KEY_USERNAME = "sub";
    private String tokenHeader = "Authorization";
    private String secret = "mysecret";
    private Long expiration = (long) (60 * 60 * 2);
    private TemporalUnit temporalUnit = ChronoUnit.SECONDS;
    private String chaimKey = CLAIM_KEY_USERNAME;
}
