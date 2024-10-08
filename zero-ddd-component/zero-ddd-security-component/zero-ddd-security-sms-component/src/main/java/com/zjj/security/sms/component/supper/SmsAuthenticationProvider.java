package com.zjj.security.sms.component.supper;

import com.zjj.security.sms.component.CodeService;
import com.zjj.security.sms.component.MobileNotFoundException;
import com.zjj.security.sms.component.SmsDetailsService;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月30日 21:32
 */
@Slf4j
public class SmsAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware {

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	private CodeService codeService;

	@Setter
	private SmsDetailsService smsDetailsService;

	@Setter
	private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

	@Setter
	@Getter
	private boolean forcePrincipalAsString = false;

	public SmsAuthenticationProvider(CodeService codeService, SmsDetailsService smsDetailsService) {
		this.codeService = codeService;
		this.smsDetailsService = smsDetailsService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.isInstanceOf(SmsAuthenticationToken.class, authentication, () -> this.messages
				.getMessage("SmsAuthenticationToken.onlySupports", "Only SmsAuthenticationToken is supported"));
		String mobile = determineMobile(authentication);

		UserDetails user;

		try {
			user = retrieveUser(mobile);
		}
		catch (MobileNotFoundException ex) {
			log.debug("Failed to find user '" + mobile + "'");
			throw new BadCredentialsException(
					this.messages.getMessage("SmsAuthenticationToken.badCredentials", "Bad credentials"));
		}
		Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");

		// this.preAuthenticationChecks.check(user);
		additionalAuthenticationChecks(mobile, (SmsAuthenticationToken) authentication);
		// this.postAuthenticationChecks.check(user);

		Object principalToReturn = user;
		if (this.forcePrincipalAsString) {
			principalToReturn = user.getUsername();
		}
		return createSuccessAuthentication(principalToReturn, authentication, user);
	}

	protected void additionalAuthenticationChecks(String mobile, SmsAuthenticationToken authentication)
			throws AuthenticationException {
		if (authentication.getCredentials() == null) {
			log.debug("Failed to authenticate since no credentials provided");
			throw new BadCredentialsException(
					this.messages.getMessage("SmsAuthenticationToken.badCredentials", "Bad credentials"));
		}
		String code = authentication.getCredentials().toString();
		if (!this.codeService.matches(mobile, code)) {
			log.debug("Failed to authenticate since password does not match stored value");
			throw new BadCredentialsException(
					this.messages.getMessage("SmsAuthenticationToken.badCredentials", "Bad credentials"));
		}
	}

	protected UserDetails retrieveUser(String mobile) throws AuthenticationException {
		try {
			UserDetails loadedUser = this.getSmsDetailsService().loadUserByMobile(mobile);
			if (loadedUser == null) {
				throw new InternalAuthenticationServiceException(
						"SmsDetailsService returned null, which is an interface contract violation");
			}
			return loadedUser;
		}
		catch (MobileNotFoundException | InternalAuthenticationServiceException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
		}
	}

	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
			UserDetails user) {

		SmsAuthenticationToken result = SmsAuthenticationToken.authenticated(principal, authentication.getCredentials(),
				this.authoritiesMapper.mapAuthorities(user.getAuthorities()));
		result.setDetails(authentication.getDetails());
		log.debug("Authenticated user");
		return result;
	}

	protected String determineMobile(Authentication authentication) {
		return (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
	}

	public void setCodeService(CodeService codeService) {
		Assert.notNull(codeService, "codeService cannot be null");
		this.codeService = codeService;
	}

	protected CodeService getCodeService() {
		return this.codeService;
	}

	protected SmsDetailsService getSmsDetailsService() {
		return this.smsDetailsService;
	}

	protected void doAfterPropertiesSet() {
		Assert.notNull(this.smsDetailsService, "A SmsDetailsService must be set");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		doAfterPropertiesSet();
	}

	@Override
	public void setMessageSource(@NonNull MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (SmsAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
