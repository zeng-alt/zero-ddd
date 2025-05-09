package com.zjj.security.core.component.configuration;

import com.zjj.autoconfigure.component.security.AbstractLoginConfigurer;
import com.zjj.autoconfigure.component.security.SecurityBuilderCustomizer;
import com.zjj.autoconfigure.component.security.SecurityProperties;
import com.zjj.security.core.component.UserAwareTaskDecorator;
import com.zjj.security.core.component.spi.AuthorizationManagerProvider;
import com.zjj.security.core.component.spi.ReactiveAuthorizationManagerProvider;
import com.zjj.security.core.component.spi.WhiteListService;
import com.zjj.security.core.component.supper.CompositeAuthorizationManager;
import com.zjj.security.core.component.supper.WhiteListAuthorizationManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.TaskDecorator;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.server.WebFilterChainProxy;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月29日 21:04
 */
@AutoConfiguration
//@ConditionalOnBean(WebSecurityConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass({ EnableWebSecurity.class })
@Import({ LoginAutoConfiguration.class, LoginHandlerAutoConfiguration.class,
		AccessFailedAutoConfiguration.class, JwtCacheManageConfiguration.class })
public class WebSecurityAutoConfiguration {


	@Autowired
	private SecurityProperties securityProperties;

	@Bean
	@ConditionalOnMissingBean(SecurityFilterChain.class)
	public SecurityFilterChain filterChain(HttpSecurity http, ObjectProvider<SecurityBuilderCustomizer> customizers,
			ObjectProvider<AuthorizationManagerProvider<RequestAuthorizationContext>> authorizationManagerProviders,
			ObjectProvider<AbstractLoginConfigurer> configurers,
			WhiteListService whiteListService,
			ObjectProvider<AuthenticationSuccessHandler> loginSuccessAuthenticationHandler,
			ObjectProvider<AuthenticationFailureHandler> loginFailureAuthenticationHandler,
			AuthenticationManager authenticationManager,
			AuthenticationEntryPoint authenticationEntryPoint, AccessDeniedHandler accessDeniedHandler,
			ApplicationEventPublisher applicationEventPublisher

	) throws Exception {
		// UserDetails
		HttpSecurity httpSecurity = http
				.csrf(AbstractHttpConfigurer::disable)
				.httpBasic(AbstractHttpConfigurer::disable)
				.cors(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(author -> {
					author
							.requestMatchers(HttpMethod.POST, "/login/**").permitAll()
							.requestMatchers("/h2-console/**").permitAll()
							.requestMatchers("/tenant/graphiql/**").permitAll()
							.requestMatchers("/tenant/graphql/**").permitAll()
							.requestMatchers(HttpMethod.POST, "/actuator/startup").permitAll();
					if (securityProperties.getEnabledAccess()) {
						author.anyRequest().access(compositeAuthorizationManager(authorizationManagerProviders, whiteListService));
					} else {
						author.anyRequest().permitAll();
					}
				})
				.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
				.exceptionHandling(e -> e
						.authenticationEntryPoint(authenticationEntryPoint)
						.accessDeniedHandler(accessDeniedHandler)
				)
				.formLogin(Customizer.withDefaults());
//		http.addFilterAfter(new CachingContentFilter(), DisableEncodeUrlFilter.class);

		// .formLogin(
		// AbstractHttpConfigurer::disable // 禁用，前后端分离项目
		// .loginPage("/login")
		// .failureHandler(loginAuthenticationHandler)
		// .successHandler(loginAuthenticationHandler)
		// .permitAll()
		// .loginPage("/login/mobilecode")
		// .failureHandler(loginAuthenticationHandler)
		// .successHandler(loginAuthenticationHandler)
		// .permitAll()
		// );

		customizers.orderedStream().forEach(customizer -> {
			try {
				customizer.customize(httpSecurity);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		});

		configurers.orderedStream().forEach(configurer -> {
			try {
				http.with(configurer, c -> {
					loginSuccessAuthenticationHandler.ifAvailable(c::successHandler);
					loginFailureAuthenticationHandler.ifAvailable(c::failureHandler);
					c.authenticationManager(authenticationManager).eventPublisher(applicationEventPublisher);
				});
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		});

		return http.build();
	}


	private CompositeAuthorizationManager compositeAuthorizationManager(ObjectProvider<AuthorizationManagerProvider<RequestAuthorizationContext>> authorizationManagerProviders, WhiteListService whiteListService) {
		List<AuthorizationManager<RequestAuthorizationContext>> list = new ArrayList<>();
		list.add(WhiteListAuthorizationManager.authenticated(whiteListService));
		List<AuthorizationManager<RequestAuthorizationContext>> managers = authorizationManagerProviders.orderedStream().map(AuthorizationManagerProvider::get).toList();
		if (CollectionUtils.isEmpty(managers)) {
			list.add(AuthenticatedAuthorizationManager.authenticated());
		}
		return new CompositeAuthorizationManager(list);
	}

//	@Bean
//	@ConditionalOnMissingBean
//	public CompositeAuthorizationManager compositeAuthorizationManager(List<AuthorizationManager<RequestAuthorizationContext>> authorizationManagers, WhiteListService whiteListService) {
//		List<AuthorizationManager<RequestAuthorizationContext>> list = new ArrayList<>();
//		list.add(WhiteListAuthorizationManager.authenticated(whiteListService));
//		list.addAll(authorizationManagers);
//		if (CollectionUtils.isEmpty(authorizationManagers)) {
//			list.add(AuthenticatedAuthorizationManager.authenticated());
//		}
//		return new CompositeAuthorizationManager(list);
//	}

	@Bean
	@ConditionalOnMissingBean(AuthenticationManager.class)
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration,
													   List<AuthenticationProvider> authenticationProviders,
													   AuthenticationEventPublisher authenticationEventPublisher

	) throws Exception {
		AuthenticationManager authenticationManager = configuration.getAuthenticationManager();
		ProviderManager providerManager = new ProviderManager(authenticationProviders, authenticationManager);
		providerManager.setAuthenticationEventPublisher(authenticationEventPublisher);
		return providerManager;
	}


	@Bean
	public TaskDecorator userAwareTaskDecorator() {
		return new UserAwareTaskDecorator();
	}

}
