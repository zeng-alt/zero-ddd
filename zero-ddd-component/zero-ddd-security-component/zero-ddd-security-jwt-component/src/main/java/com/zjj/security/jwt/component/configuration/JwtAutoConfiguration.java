package com.zjj.security.jwt.component.configuration;

import com.zjj.autoconfigure.component.security.SecurityBuilderCustomizer;
import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtHelper;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import com.zjj.security.jwt.component.JwtAuthenticationTokenFilter;
import com.zjj.security.jwt.component.JwtRenewFilter;
import com.zjj.security.jwt.component.supper.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月26日 20:21
 */
@Configuration
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(name = "security.jwt.enabled", havingValue = "true", matchIfMissing = true)
public class JwtAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(JwtHelper jwtHelper,
																	 JwtCacheManage jwtCacheManage, JwtProperties jwtProperties) {

		return new DefaultJwtAuthenticationTokenFilter(jwtHelper, jwtCacheManage, jwtProperties);
	}

	@Bean
	@ConditionalOnMissingBean
	public JwtRenewFilter jwtRenewFilter(JwtCacheManage jwtCacheManage) {
		return new DefaultJwtRenewFilter(jwtCacheManage);
	}

	@Order(9)
	@Bean
	public SecurityBuilderCustomizer jwtCustomizer(JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter,
			JwtRenewFilter jwtRenewFilter) {
		return http -> {
			Class type = ResolvableType.forType(jwtAuthenticationTokenFilter.getClass()).getRawClass();
			http.addFilterBefore(jwtAuthenticationTokenFilter, LogoutFilter.class);
			http.addFilterAfter(jwtRenewFilter, type);
		};
	}

}
