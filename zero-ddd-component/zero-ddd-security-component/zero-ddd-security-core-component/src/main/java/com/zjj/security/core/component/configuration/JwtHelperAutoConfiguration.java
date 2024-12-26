package com.zjj.security.core.component.configuration;

import com.zjj.autoconfigure.component.json.JsonHelper;
import com.zjj.autoconfigure.component.l2cache.L2CacheManage;
import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtHelper;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import com.zjj.cache.component.config.RedisAutoConfiguration;
import com.zjj.security.core.component.configuration.properties.LoginProperties;
import com.zjj.security.core.component.supper.DefaultJwtCacheManage;
import com.zjj.security.core.component.supper.DefaultJwtHelper;
import com.zjj.security.core.component.supper.DefaultJwtL2CacheManage;
import com.zjj.security.core.component.supper.JwtCacheProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

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
	public JwtHelper jwtHelper(JwtProperties jwtProperties) {
		return new DefaultJwtHelper(jwtProperties);
	}


	@Bean
	@ConditionalOnBean(L2CacheManage.class)
	public JwtCacheProvider jwtCacheProvider(JwtProperties jwtProperties) {
		return new JwtCacheProvider(jwtProperties);
	}


}
