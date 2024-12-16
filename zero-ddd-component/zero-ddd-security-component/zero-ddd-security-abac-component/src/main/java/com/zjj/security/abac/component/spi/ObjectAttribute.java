package com.zjj.security.abac.component.spi;

import io.vavr.Tuple2;
import reactor.core.publisher.Mono;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月13日 21:14
 */
public interface ObjectAttribute {

    Object getObject();

    String getObjectName();
}
