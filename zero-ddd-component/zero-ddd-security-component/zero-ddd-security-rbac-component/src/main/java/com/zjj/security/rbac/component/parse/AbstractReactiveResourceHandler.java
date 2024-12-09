package com.zjj.security.rbac.component.parse;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月05日 17:17
 */
public abstract class AbstractReactiveResourceHandler implements ReactiveResourceHandler {

    public final ReactiveResourceQueryManager reactiveResourceQueryManager;

    protected AbstractReactiveResourceHandler(ReactiveResourceQueryManager reactiveResourceQueryManager) {
        this.reactiveResourceQueryManager = reactiveResourceQueryManager;
    }
}
