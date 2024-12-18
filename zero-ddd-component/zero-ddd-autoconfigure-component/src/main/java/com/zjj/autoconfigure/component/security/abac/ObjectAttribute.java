package com.zjj.autoconfigure.component.security.abac;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月13日 21:14
 */
public interface ObjectAttribute<T> {

    T getObject();

    String getPolicyKey();
}
