package com.zjj.security.captcha.component.supper;

import com.zjj.autoconfigure.component.security.AuthenticationHelper;
import com.zjj.security.captcha.component.configuration.CaptchaProperties;
import com.zjj.security.captcha.component.spi.CaptchaFailureHandler;
import com.zjj.security.captcha.component.spi.CaptchaSuccessHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月30日 21:15
 */
@Getter
@Setter
public class CaptchaAuthenticationFilter extends OncePerRequestFilter {

    private static final OrRequestMatcher CAPTCHA_MATCHER = new OrRequestMatcher(new AntPathRequestMatcher("/login/**", "POST"));
    private static final String CAPTCHA_PATH = "captcha";
    private static final String CAPTCHA_KEY = "uuid";
    private OrRequestMatcher captchaMatcher = CAPTCHA_MATCHER;
    private String captchaParameter = CAPTCHA_PATH;
    private String captchaKey = CAPTCHA_KEY;
    private AuthenticationManager authenticationManager;
    private CaptchaSuccessHandler successHandler = (request, response, authentication) -> {};
    private CaptchaFailureHandler failureHandler = (request, response, exception) -> AuthenticationHelper.renderString(response, 10003, "验证码错误");
    public CaptchaAuthenticationFilter() {}

    public CaptchaAuthenticationFilter(CaptchaProperties captchaProperties) {
        this(captchaProperties.getFilterUrl(), captchaProperties.getCaptchaParameter(), captchaProperties.getCaptchaKeyParameter());
    }

    public CaptchaAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    public CaptchaAuthenticationFilter(List<String> paths) {
        this(paths, CAPTCHA_PATH, CAPTCHA_KEY);
    }

    public CaptchaAuthenticationFilter(List<String> paths, String captchaPath, String captchaKey) {
        this.captchaParameter = captchaPath;
        this.captchaKey = captchaKey;
        List<AntPathRequestMatcher> list = paths.stream().map(p -> new AntPathRequestMatcher(p, HttpMethod.POST.name())).toList();
        this.captchaMatcher = new OrRequestMatcher(list.toArray(new RequestMatcher[0]));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (!requiresAuthentication(request, response)) {
            chain.doFilter(request, response);
            return;
        }
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String captcha = obtainCaptcha(request);
        captcha = (captcha != null) ? captcha.trim() : "";

        String captchaKey = obtainCaptchaKey(request);
        captchaKey = (captchaKey != null) ? captchaKey.trim() : "";
        CaptchaAuthenticationToken authRequest = CaptchaAuthenticationToken.unauthenticated(captchaKey, captcha);
        try {
            Authentication authenticate = this.getAuthenticationManager().authenticate(authRequest);
            if (authenticate == null) {
                return;
            }
            chain.doFilter(request, response);
            successfulAuthentication(request, response, chain, authenticate);
        } catch (AuthenticationException e) {
            unsuccessfulAuthentication(request, response, e);
        }
    }


    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(LogMessage.format("Set SecurityContextHolder to %s", authResult));
        }
//        if (this.eventPublisher != null) {
//            this.eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
//        }
        this.successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    /**
     * Default behaviour for unsuccessful authentication.
     * <ol>
     * <li>Clears the {@link SecurityContextHolder}</li>
     * <li>Stores the exception in the session (if it exists or
     * <tt>allowSessionCreation</tt> is set to <tt>true</tt>)</li>
     * <li>Informs the configured <tt>RememberMeServices</tt> of the failed login</li>
     * <li>Delegates additional behaviour to the
     * {@link AuthenticationFailureHandler}.</li>
     * </ol>
     */
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        this.logger.trace("Failed to process authentication request", failed);
        this.logger.trace("Handling authentication failure");
        this.failureHandler.onAuthenticationFailure(request, response, failed);
    }

    protected AuthenticationManager getAuthenticationManager() {
        return this.authenticationManager;
    }

    protected String obtainCaptcha(HttpServletRequest request) {
        return request.getParameter(this.captchaParameter);
    }

    protected String obtainCaptchaKey(HttpServletRequest request) {
        return request.getParameter(this.captchaKey);
    }

    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        if (this.captchaMatcher.matches(request)) {
            return true;
        }
        if (this.logger.isTraceEnabled()) {
            this.logger
                    .trace(LogMessage.format("Did not match request to %s", this.captchaMatcher));
        }
        return false;
    }

    public void setRequiresAuthenticationRequestMatcher(RequestMatcher loginProcessingUrlMatcher) {
        this.captchaMatcher = new OrRequestMatcher(loginProcessingUrlMatcher);
    }

    public void setRequiresAuthenticationRequestMatcher(List<RequestMatcher> loginProcessingUrlMatcher) {
        this.captchaMatcher = new OrRequestMatcher(loginProcessingUrlMatcher);
    }
}
