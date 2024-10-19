package com.zjj.security.core.component.configuration;

import com.zjj.autoconfigure.component.json.JsonHelper;
import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtHelper;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import com.zjj.security.core.component.configuration.properties.LoginProperties;
import com.zjj.security.core.component.supper.DefaultJwtCacheManage;
import com.zjj.security.core.component.supper.DefaultJwtHelper;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月30日 09:41
 */
@AutoConfiguration
@EnableConfigurationProperties({ JwtProperties.class, LoginProperties.class })
public class JwtHelperAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public JwtHelper jwtHelper(JwtProperties jwtProperties, JsonHelper jsonHelper) {
		return new DefaultJwtHelper(jwtProperties, jsonHelper);
	}

	@Bean
	@ConditionalOnMissingBean
	public JwtCacheManage jwtCacheManage(JwtProperties jwtProperties) {
		return new DefaultJwtCacheManage(jwtProperties);
	}

}
