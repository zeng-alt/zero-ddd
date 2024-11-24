package com.zjj.core.component.api;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月22日 22:47
 */
public interface Parent<P extends Comparable<P>> {

    P parent();

    P current();

    default boolean isRoot() {
        return parent() == null;
    }
}
