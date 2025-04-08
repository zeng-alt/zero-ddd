package com.zjj.main.domain.parameter;

import io.vavr.control.Option;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月28日 14:13
 */
public interface ParameterRepository {

    Option<ParameterAgg> findByParameterKey(String parameterKey);
}
