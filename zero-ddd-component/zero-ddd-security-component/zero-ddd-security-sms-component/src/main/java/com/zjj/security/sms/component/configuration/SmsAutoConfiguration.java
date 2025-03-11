package com.zjj.security.sms.component.configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.zjj.autoconfigure.component.security.AbstractLoginConfigurer;
import com.zjj.memory.component.provider.CaffeineCacheProvider;
import com.zjj.memory.component.provider.Tuple;
import com.zjj.security.sms.component.CodeService;
import com.zjj.security.sms.component.SmsDetailsService;
import com.zjj.security.sms.component.supper.DefaultCodeService;
import com.zjj.security.sms.component.supper.DefaultSmsDetailsService;
import com.zjj.security.sms.component.supper.SmsAuthenticationFilter;
import com.zjj.security.sms.component.supper.SmsAuthenticationProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import java.util.concurrent.TimeUnit;

/**
 * @author zengJiaJun
 * @crateTime 2024年10月06日 22:50
 * @version 1.0
 */
@AutoConfiguration
@EnableConfigurationProperties({ SmsLoginProperties.class })
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(name = "security.sms.enabled", havingValue = "true", matchIfMissing = true)
public class SmsAutoConfiguration {

	@Bean
	public AbstractLoginConfigurer<DefaultSmsLoginConfigurer, HttpSecurity, SmsAuthenticationFilter> smsLoginConfigurer(
			SmsLoginProperties smsLoginProperties) {
		return new DefaultSmsLoginConfigurer(smsLoginProperties, new SmsAuthenticationFilter());
	}

	private Cache<Object, Object> codeCache() {
		return Caffeine
				.newBuilder()
				.initialCapacity(100)
				.maximumSize(500)
				.expireAfterAccess(1, TimeUnit.MINUTES)
				.weakKeys()
				.recordStats()
				.build();
	}

	@Bean
	@ConditionalOnMissingBean
	public CodeService codeService(CacheManager codeCache) {
		return new DefaultCodeService(codeCache.getCache("code"));
	}

	@Bean
	// @ConditionalOnBean(CodeService.class)
	public CaffeineCacheProvider codeCacheProvider() {
		return () -> Tuple.of("code", codeCache());
	}

	@Bean
	@ConditionalOnMissingBean
	public SmsDetailsService smsDetailsService() {
		return new DefaultSmsDetailsService();
	}

	@Bean
	@ConditionalOnMissingBean
	public SmsAuthenticationProvider smsAuthenticationProvider(CodeService codeService,
			SmsDetailsService smsDetailsService) {
		return new SmsAuthenticationProvider(codeService, smsDetailsService);
	}

}
