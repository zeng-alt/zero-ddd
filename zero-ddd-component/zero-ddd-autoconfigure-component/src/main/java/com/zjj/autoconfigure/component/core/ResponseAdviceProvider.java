package com.zjj.autoconfigure.component.core;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月06日 14:51
 */
@FunctionalInterface
public interface ResponseAdviceProvider {

    public Response handle(Response response);
}
