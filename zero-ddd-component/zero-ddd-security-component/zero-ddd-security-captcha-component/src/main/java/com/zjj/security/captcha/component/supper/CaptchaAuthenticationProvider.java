package com.zjj.security.captcha.component.supper;

import com.zjj.security.captcha.component.spi.CaptchaService;
import lombok.NonNull;
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
import org.springframework.util.Assert;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月30日 16:04
 */
@Slf4j
public class CaptchaAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware {
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();


    private CaptchaService captchaService;

    public CaptchaAuthenticationProvider(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(CaptchaAuthenticationToken.class, authentication, () -> this.messages
                .getMessage("SmsAuthenticationToken.onlySupports", "Only SmsAuthenticationToken is supported"));
        String captchaKey = determineMobile(authentication);


        String captcha;

        try {
            captcha = retrieveCaptcha(captchaKey);
        }
        catch (CaptchaNotFoundException ex) {
            log.debug("Failed to find captcha '" + captchaKey + "'");
            throw new BadCredentialsException(
                    this.messages.getMessage("SmsAuthenticationToken.badCredentials", "Bad credentials"));
        }
        Assert.notNull(captcha, "retrieveUser returned null - a violation of the interface contract");

        additionalAuthenticationChecks(captcha, (CaptchaAuthenticationToken) authentication);

        return createSuccessAuthentication(captchaKey, authentication);
    }

    private String retrieveCaptcha(String captchaKey) {
        try {
            String captcha = this.getCaptchaService().getCaptcha(captchaKey);
            if (captcha == null) {
                throw new InternalAuthenticationServiceException(
                        "CaptchaService returned null, which is an interface contract violation");
            }
            return captcha;
        } catch (CaptchaNotFoundException | InternalAuthenticationServiceException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }

    protected void additionalAuthenticationChecks(String captcha, CaptchaAuthenticationToken authentication)
            throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            log.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException(
                    this.messages.getMessage("SmsAuthenticationToken.badCredentials", "Bad credentials"));
        }
        String sourceCaptcha = authentication.getCredentials().toString();
        if (!captcha.equals(sourceCaptcha)) {
            log.debug("Failed to authenticate since password does not match stored value");
            throw new BadCredentialsException(
                    this.messages.getMessage("SmsAuthenticationToken.badCredentials", "Bad credentials"));
        }
    }

    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication) {

        CaptchaAuthenticationToken result = CaptchaAuthenticationToken.authenticated(principal, authentication.getCredentials());
        log.debug("Authenticated user");
        return result;
    }

    protected String determineMobile(Authentication authentication) {
        return (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
    }

    public void setCaptchaService(CaptchaService captchaService) {
        Assert.notNull(captchaService, "codeService cannot be null");
        this.captchaService = captchaService;
    }

    protected CaptchaService getCaptchaService() {
        return this.captchaService;
    }


    protected void doAfterPropertiesSet() {
        Assert.notNull(this.captchaService, "A captchaService must be set");
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
        return (CaptchaAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
