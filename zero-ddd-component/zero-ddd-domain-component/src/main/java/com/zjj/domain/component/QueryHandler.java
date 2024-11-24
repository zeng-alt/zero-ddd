package com.zjj.domain.component;

import io.vavr.control.Option;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月18日 16:05
 */
public interface QueryHandler<Q extends Query> {

    <E> Option<E> query(Q q);
}
