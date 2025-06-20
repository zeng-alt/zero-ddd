package com.zjj.main.domain.rule;

import io.vavr.control.Option;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月16日 10:05
 */
public interface PolicyRuleRepository {

    Option<PolicyRule> findById(Long id);
}
