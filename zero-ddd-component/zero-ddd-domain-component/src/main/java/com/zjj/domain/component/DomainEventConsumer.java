package com.zjj.domain.component;

import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月06日 21:31
 */
@FunctionalInterface
public interface DomainEventConsumer<T> extends Consumer<T> {
}
