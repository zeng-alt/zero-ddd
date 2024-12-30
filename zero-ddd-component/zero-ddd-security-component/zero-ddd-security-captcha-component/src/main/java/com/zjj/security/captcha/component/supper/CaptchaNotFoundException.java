package com.zjj.security.captcha.component.supper;

import org.springframework.security.core.AuthenticationException;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月30日 21:07
 */
public class CaptchaNotFoundException extends AuthenticationException {

    public CaptchaNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Constructs an {@code AuthenticationException} with the specified message and root
     * cause.
     *
     * @param msg   the detail message
     * @param cause the root cause
     */
    public CaptchaNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
