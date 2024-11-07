package com.zjj.security.core.component.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjj.autoconfigure.component.json.JsonHelper;
import com.zjj.autoconfigure.component.security.AbstractLoginConfigurer;
import com.zjj.autoconfigure.component.security.SecurityBuilderCustomizer;
import com.zjj.security.core.component.filter.CachingContentFilter;
import io.micrometer.core.instrument.util.IOUtils;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.security.web.session.DisableEncodeUrlFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.function.Supplier;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月29日 21:04
 */
@AutoConfiguration
@Import({ LoginAutoConfiguration.class, JwtHelperAutoConfiguration.class, LoginHandlerAutoConfiguration.class,
		AccessFailedAutoConfiguration.class })
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebSecurityAutoConfiguration {

	@Autowired
	private JsonHelper jsonHelper;
	@Autowired
	private ObjectMapper objectMapper;

	@Bean
	@ConditionalOnMissingBean(SecurityFilterChain.class)
	public SecurityFilterChain filterChain(HttpSecurity http, ObjectProvider<SecurityBuilderCustomizer> customizers,
			ObjectProvider<AbstractLoginConfigurer> configurers,
			AuthenticationSuccessHandler loginSuccessAuthenticationHandler,
			AuthenticationFailureHandler loginFailureAuthenticationHandler, AuthenticationManager authenticationManager,
			AuthenticationEntryPoint authenticationEntryPoint, AccessDeniedHandler accessDeniedHandler,
			ApplicationEventPublisher applicationEventPublisher

	) throws Exception {
		// UserDetails
		HttpSecurity httpSecurity = http
				.csrf(AbstractHttpConfigurer::disable)
				.httpBasic(AbstractHttpConfigurer::disable)
				.cors(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(author -> author
						.requestMatchers(HttpMethod.POST, "/login/**").permitAll()
//						.requestMatchers("/h2-console/**").permitAll()
						.requestMatchers("/graphiql/**").permitAll()
//						.requestMatchers("/graphql/**").permitAll()
						.anyRequest().access(new AuthorizationManager<RequestAuthorizationContext>() {

							@Override
							public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
								HttpServletRequest request = object.getRequest();
                                try {
									ServletRequest request1 = ((SecurityContextHolderAwareRequestWrapper) object.getRequest()).getRequest();
//									((HeaderWriterFilter.HeaderWriterRequest) request1).getRequest();
									String string = IOUtils.toString(request.getInputStream(), UTF_8);
//									jsonHelper.
									System.out.println();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                                return new AuthorizationDecision(true);
							}

							public byte[] getRequestPostBytes(HttpServletRequest request)
									throws IOException {

								ContentCachingRequestWrapper request1 = (ContentCachingRequestWrapper) request;
								return request1.getContentAsByteArray();
							}

							/**
							 * 描述:获取 post 请求内容
							 * <pre>
							 * 举例：
							 * </pre>
							 * @param request
							 * @return
							 * @throws IOException
							 */
							public String getRequestPostStr(HttpServletRequest request)
									throws IOException {
								byte buffer[] = getRequestPostBytes(request);
								String charEncoding = request.getCharacterEncoding();
								if (charEncoding == null) {
									charEncoding = "UTF-8";
								}
								return new String(buffer, charEncoding);
							}
						})
				)
				.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
				.exceptionHandling(e -> e
						.authenticationEntryPoint(authenticationEntryPoint)
						.accessDeniedHandler(accessDeniedHandler)
				)
				.formLogin(Customizer.withDefaults());
		http.addFilterAfter(new CachingContentFilter(), HeaderWriterFilter.class);

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
				http.with(configurer, c -> c.successHandler(loginSuccessAuthenticationHandler)
						.failureHandler(loginFailureAuthenticationHandler).authenticationManager(authenticationManager)
						.eventPublisher(applicationEventPublisher));
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		});

		return http.build();
	}

}
