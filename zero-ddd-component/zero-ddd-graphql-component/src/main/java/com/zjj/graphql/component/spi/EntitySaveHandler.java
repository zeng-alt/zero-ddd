package com.zjj.graphql.component.spi;

import com.zjj.domain.component.BaseEntity;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月27日 09:41
 */
@FunctionalInterface
public interface EntitySaveHandler<T> {

    void handler(T entity);
}
