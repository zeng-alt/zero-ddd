package com.zjj.security.captcha.component.supper;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.util.Assert;

import java.io.Serial;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月30日 21:37
 */
public class CaptchaAuthenticationToken extends AbstractAuthenticationToken {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Object principal;
    private Object credentials;

    public CaptchaAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(false);
    }

    public static CaptchaAuthenticationToken unauthenticated(Object principal, Object credentials) {
        return new CaptchaAuthenticationToken(principal, credentials);
    }

    public static CaptchaAuthenticationToken authenticated(Object principal, Object credentials) {
        CaptchaAuthenticationToken captchaAuthenticationToken = new CaptchaAuthenticationToken(principal, credentials);
        captchaAuthenticationToken.setAuthenticated(true);
        return captchaAuthenticationToken;
    }

    /**
     * The credentials that prove the principal is correct. This is usually a password,
     * but could be anything relevant to the <code>AuthenticationManager</code>. Callers
     * are expected to populate the credentials.
     *
     * @return the credentials that prove the identity of the <code>Principal</code>
     */
    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    /**
     * The identity of the principal being authenticated. In the case of an authentication
     * request with username and password, this would be the username. Callers are
     * expected to populate the principal for an authentication request.
     * <p>
     * The <tt>AuthenticationManager</tt> implementation will often return an
     * <tt>Authentication</tt> containing richer information as the principal for use by
     * the application. Many of the authentication providers will create a
     * {@code UserDetails} object as the principal.
     *
     * @return the <code>Principal</code> being authenticated or the authenticated
     * principal after authentication.
     */
    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}
