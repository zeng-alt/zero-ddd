package com.zjj.main.application.service;

import com.zjj.autoconfigure.component.security.abac.PolicyRule;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月15日 20:47
 */
public interface PolicyRuleService {

    public PolicyRule findRuleByCode(String code, boolean isPreAuth);
}
