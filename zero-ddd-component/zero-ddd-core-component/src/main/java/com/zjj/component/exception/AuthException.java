package com.zjj.component.exception;

/**
 * @author zengJiaJun
 * @crateTime 2024年06月17日 21:38
 * @version 1.0
 */
public class AuthException extends RuntimeException {

    public AuthException(String message) {
        super(message);
    }
}
