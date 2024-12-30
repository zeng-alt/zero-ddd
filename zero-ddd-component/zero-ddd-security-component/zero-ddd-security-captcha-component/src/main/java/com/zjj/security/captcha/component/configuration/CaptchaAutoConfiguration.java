package com.zjj.security.captcha.component.configuration;

import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.autoconfigure.component.security.SecurityBuilderCustomizer;
import com.zjj.security.captcha.component.spi.CaptchaFailureHandler;
import com.zjj.security.captcha.component.spi.CaptchaService;
import com.zjj.security.captcha.component.spi.CaptchaSuccessHandler;
import com.zjj.security.captcha.component.supper.CaptchaAuthenticationFilter;
import com.zjj.security.captcha.component.supper.CaptchaAuthenticationProvider;
import com.zjj.security.captcha.component.supper.DefaultCaptchaService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * @author zengJiaJun
 * @crateTime 2024年10月06日 22:50
 * @version 1.0
 */
@AutoConfiguration
@EnableConfigurationProperties({ CaptchaProperties.class })
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(name = "security.captcha.enabled", havingValue = "true", matchIfMissing = true)
public class CaptchaAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public CaptchaSuccessHandler captchaSuccessHandler(CaptchaService captchaService) {
		return (request, response, authentication) -> captchaService.removeCaptcha(authentication.getPrincipal().toString());
	}


	@Bean
	@ConditionalOnBean(RedisStringRepository.class)
	@ConditionalOnMissingBean
	public CaptchaService captchaService(RedisStringRepository redisStringRepository, CaptchaProperties captchaProperties) {
		return new DefaultCaptchaService(redisStringRepository, captchaProperties);
	}

	@Bean
	public CaptchaAuthenticationProvider captchaAuthenticationProvider(CaptchaService captchaService) {
		return new CaptchaAuthenticationProvider(captchaService);
	}

	@Bean
	public SecurityBuilderCustomizer captchaCustomizer(
			CaptchaProperties captchaProperties,
			AuthenticationManager authenticationManager,
			ObjectProvider<CaptchaSuccessHandler> captchaSuccessHandler,
			ObjectProvider<CaptchaFailureHandler> captchaFailureHandler) {

		CaptchaAuthenticationFilter captchaAuthenticationFilter = new CaptchaAuthenticationFilter(captchaProperties);
		captchaAuthenticationFilter.setAuthenticationManager(authenticationManager);
		captchaSuccessHandler.ifAvailable(captchaAuthenticationFilter::setSuccessHandler);
		captchaFailureHandler.ifAvailable(captchaAuthenticationFilter::setFailureHandler);
		return http -> http.addFilterAfter(captchaAuthenticationFilter, LogoutFilter.class);
	}



}
