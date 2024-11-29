package com.zjj.autoconfigure.component.security.jwt;

import com.zjj.autoconfigure.component.security.jwt.validator.ValidExpiration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月26日 20:50
 */
@Data
@Component
@Validated
@ValidExpiration
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

	private static final String CLAIM_KEY_USERNAME = "sub";

	private Boolean enabled = true;

	private String tokenHeader = "Authorization";
	private String fastToken = "User";

	private String secret = "mysecret";

	private Long expiration = (long) (20);

	private TemporalUnit temporalUnit = ChronoUnit.MINUTES;

	private String chaimKey = CLAIM_KEY_USERNAME;

}
