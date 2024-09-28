package com.zjj.autoconfigure.component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月27日 20:03
 */
public class UtilException extends RuntimeException {

    public UtilException() {}

    public UtilException(String msg) {
        super(msg);
    }

    public UtilException(Exception e) {
        super(e);
    }
}
