package com.zjj.security.sms.component;

import org.springframework.security.core.AuthenticationException;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月30日 20:42
 */
public class MobileNotFoundException extends AuthenticationException {

	/**
	 * Constructs a <code>MobileNotFoundException</code> with the specified message.
	 * @param msg the detail message.
	 */
	public MobileNotFoundException(String msg) {
		super(msg);
	}

	/**
	 * Constructs a {@code MobileNotFoundException} with the specified message and root
	 * cause.
	 * @param msg the detail message.
	 * @param cause root cause
	 */
	public MobileNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
