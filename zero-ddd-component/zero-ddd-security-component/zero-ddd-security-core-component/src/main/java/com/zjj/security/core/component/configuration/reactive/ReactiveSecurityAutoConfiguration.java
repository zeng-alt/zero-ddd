package com.zjj.security.core.component.configuration.reactive;

import com.zjj.autoconfigure.component.security.SecurityProperties;
import com.zjj.autoconfigure.component.security.ServerHttpSecurityBuilderCustomizer;
import com.zjj.security.core.component.configuration.UsernameLoginProperties;
import com.zjj.security.core.component.spi.ReactiveAuthorizationManagerProvider;
import com.zjj.security.core.component.spi.WhiteListService;
import com.zjj.security.core.component.supper.CompositeReactiveAuthorizationManager;
import com.zjj.security.core.component.supper.ReactiveWhiteListAuthorizationManager;
import com.zjj.security.core.component.supper.reactive.DefaultReactiveAccessDeniedHandler;
import com.zjj.security.core.component.supper.reactive.DefaultReactiveAuthenticationEntryPoint;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthenticatedReactiveAuthorizationManager;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.WebFilterChainProxy;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月29日 21:04
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@ConditionalOnClass({ EnableWebFluxSecurity.class, WebFilterChainProxy.class, })
@Import({ReactiveJwtCacheManageConfiguration.class})
public class ReactiveSecurityAutoConfiguration {

	@Autowired
	private SecurityProperties securityProperties;
	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(
			ServerHttpSecurity http,
			ObjectProvider<ReactiveAuthorizationManagerProvider<AuthorizationContext>> reactiveAuthorizationManagerProviders,
			ObjectProvider<ServerHttpSecurityBuilderCustomizer> customizers,
			WhiteListService whiteListService,
			ServerAccessDeniedHandler serverAccessDeniedHandler,
			ServerAuthenticationEntryPoint serverAuthenticationEntryPoint
	) {

		http.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);
		http.csrf(ServerHttpSecurity.CsrfSpec::disable);
		http.formLogin(ServerHttpSecurity.FormLoginSpec::disable);
//		http.formLogin(Customizer.withDefaults());
		http.logout(ServerHttpSecurity.LogoutSpec::disable);
		http.exceptionHandling(ex -> ex.authenticationEntryPoint(serverAuthenticationEntryPoint).accessDeniedHandler(serverAccessDeniedHandler));
		http.authorizeExchange(authorizeExchangeSpec -> {
			authorizeExchangeSpec
					.pathMatchers(HttpMethod.POST, "/login/**").permitAll();
			if (securityProperties.getEnabledAccess()) {
				authorizeExchangeSpec.anyExchange().access(compositeReactiveAuthorizationManager(reactiveAuthorizationManagerProviders, whiteListService));
			} else {
				authorizeExchangeSpec.anyExchange().permitAll();
			}
		});
		customizers.orderedStream().forEach(customizer -> customizer.customizer(http));
		return http.build();
	}

	private CompositeReactiveAuthorizationManager compositeReactiveAuthorizationManager(ObjectProvider<ReactiveAuthorizationManagerProvider<AuthorizationContext>> reactiveAuthorizationManagerProviders, WhiteListService whiteListService) {
		List<ReactiveAuthorizationManager<AuthorizationContext>> list = new ArrayList<>();
		list.add(ReactiveWhiteListAuthorizationManager.authenticated(whiteListService));
		List<ReactiveAuthorizationManager<AuthorizationContext>> managers = reactiveAuthorizationManagerProviders.orderedStream().map(ReactiveAuthorizationManagerProvider::get).toList();
		list.addAll(managers);
		if (CollectionUtils.isEmpty(managers)) {
			list.add(AuthenticatedReactiveAuthorizationManager.authenticated());
		}
		return new CompositeReactiveAuthorizationManager(list);
	}


//	@Bean
//	@ConditionalOnMissingBean
//	public CompositeReactiveAuthorizationManager compositeReactiveAuthorizationManager(ObjectProvider<ReactiveAuthorizationManager<AuthorizationContext>> reactiveAuthorizationManagers, WhiteListService whiteListService) {
//		List<ReactiveAuthorizationManager<AuthorizationContext>> list = new ArrayList<>();
//		list.add(ReactiveWhiteListAuthorizationManager.authenticated(whiteListService));
//		List<ReactiveAuthorizationManager<AuthorizationContext>> managers = reactiveAuthorizationManagers.orderedStream().toList();
//		list.addAll(managers);
//		if (CollectionUtils.isEmpty(managers)) {
//			list.add(AuthenticatedReactiveAuthorizationManager.authenticated());
//		}
//		return new CompositeReactiveAuthorizationManager(list);
//	}

//	@Bean
//	@ConditionalOnProperty(name = "security.username-login.enabled", havingValue = "true")
	public ServerHttpSecurityBuilderCustomizer initiateLoginCustomizer(UsernameLoginProperties usernameLoginProperties) {
		return http -> http.formLogin(formLogin -> formLogin
				.requiresAuthenticationMatcher(new ServerWebExchangeMatcher() {
					@Override
					public Mono<MatchResult> matches(ServerWebExchange exchange) {
						return null;
					}
				})
//						.authenticationManager()

//						.authenticationManager()
//				.authenticationSuccessHandler(null)
//				.authenticationFailureHandler()
//				.authenticationEntryPoint()

		);
	}


	@Bean
	@ConditionalOnMissingBean
	public ServerAccessDeniedHandler serverAccessDeniedHandler() {
		return DefaultReactiveAccessDeniedHandler.handler();
	}

	@Bean
	@ConditionalOnMissingBean
	public ServerAuthenticationEntryPoint serverAuthenticationEntryPoint() {
		return DefaultReactiveAuthenticationEntryPoint.handler();
	}
//
//	@Bean
//	@ConditionalOnProperty(name = "security.username-login.enabled", havingValue = "false", matchIfMissing = true)
//	public ServerHttpSecurityBuilderCustomizer shutDownLoginCustomizer() {
//		return http -> http.formLogin(ServerHttpSecurity.FormLoginSpec::disable).logout(ServerHttpSecurity.LogoutSpec::disable);
//	}

}
