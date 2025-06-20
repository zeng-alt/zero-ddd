package com.zjj.main.application.service.impl;

import com.zjj.autoconfigure.component.security.abac.AbacCacheManage;
import com.zjj.autoconfigure.component.security.abac.PolicyRule;
import com.zjj.main.application.service.PolicyRuleService;
import com.zjj.main.infrastructure.db.jpa.dao.PolicyRuleEntityDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月15日 20:48
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PolicyRuleServiceImpl implements PolicyRuleService {

    private final PolicyRuleEntityDao ruleEntityDao;
    private final AbacCacheManage abacCacheManage;

    @Override
    public PolicyRule findRuleByCode(String code, boolean isPreAuth) {

        PolicyRule policyRule = abacCacheManage.getPolicyRule(code, isPreAuth);
        if (policyRule != null) {
            return policyRule;
        }

        return ruleEntityDao.findByPermissionCodeAndPreAuth(code, isPreAuth)
                .map(entity -> {
                    PolicyRule policy = new PolicyRule();
                    BeanUtils.copyProperties(entity, policy);
                    abacCacheManage.putRule(code, policy, isPreAuth);
                    return policy;
                }).getOrElseThrow(() -> new IllegalArgumentException(code + " 策略不存在"));
    }
}
