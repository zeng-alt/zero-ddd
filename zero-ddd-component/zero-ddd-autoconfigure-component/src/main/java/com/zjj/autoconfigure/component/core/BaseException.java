package com.zjj.autoconfigure.component.core;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月07日 21:15
 */
public class BaseException extends RuntimeException{

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }
}
