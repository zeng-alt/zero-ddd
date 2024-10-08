package com.zjj.security.sms.component.configuration;

import com.zjj.autoconfigure.component.security.AbstractLoginConfigurer;
import com.zjj.security.sms.component.supper.SmsAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月30日 20:43
 */
public class DefaultSmsLoginConfigurer
		extends AbstractLoginConfigurer<DefaultSmsLoginConfigurer, HttpSecurity, SmsAuthenticationFilter> {

	private final SmsLoginProperties smsLoginProperties;

	public DefaultSmsLoginConfigurer(SmsLoginProperties smsLoginProperties,
			SmsAuthenticationFilter smsAuthenticationFilter) {
		super(smsAuthenticationFilter);
		this.smsLoginProperties = smsLoginProperties;
		smsAuthenticationFilter
				.setRequiresAuthenticationRequestMatcher(createLoginProcessingUrlMatcher(smsLoginProperties.getUrl()));
		smsAuthenticationFilter.setCodeParameter(smsLoginProperties.getCodeParameter());
		smsAuthenticationFilter.setMobileParameter(smsAuthenticationFilter.getMobileParameter());
	}

	@Override
	public void init(HttpSecurity http) throws Exception {
		http.addFilterAfter(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
		return new AntPathRequestMatcher(loginProcessingUrl, "POST");
	}

	public DefaultSmsLoginConfigurer processingUrl(String loginProcessingUrl) {
		getAuthenticationFilter()
				.setRequiresAuthenticationRequestMatcher(createLoginProcessingUrlMatcher(loginProcessingUrl));
		return this;
	}

	public DefaultSmsLoginConfigurer mobileParameter(String mobileParameter) {
		getAuthenticationFilter().setMobileParameter(mobileParameter);
		return this;
	}

	public DefaultSmsLoginConfigurer codeParameter(String codeParameter) {
		getAuthenticationFilter().setCodeParameter(codeParameter);
		return this;
	}

}
