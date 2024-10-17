package com.zjj.graphql.component.exception;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月17日 20:35
 */
public class ScalarFileReadException extends RuntimeException {

    public ScalarFileReadException(Exception e) {
        super(e);
    }
}
