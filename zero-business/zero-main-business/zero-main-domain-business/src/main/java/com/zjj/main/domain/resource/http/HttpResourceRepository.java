package com.zjj.main.domain.resource.http;

import io.vavr.control.Option;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月17日 17:22
 */
public interface HttpResourceRepository {

    public Option<HttpResourceAgg> findById(Long id);
}
