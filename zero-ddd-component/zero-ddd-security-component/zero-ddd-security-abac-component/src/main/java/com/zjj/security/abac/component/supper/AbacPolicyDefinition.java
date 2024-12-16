package com.zjj.security.abac.component.supper;

import com.zjj.autoconfigure.component.security.abac.PolicyDefinition;
import com.zjj.autoconfigure.component.security.abac.PolicyRule;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月16日 21:19
 */
@RequiredArgsConstructor
public class AbacPolicyDefinition {

    private final PolicyDefinition policyDefinition;

    public PolicyRule getPolicyRule(String tenant, String key, String typeClass) {
        return policyDefinition.getPolicyRule(tenant, key, typeClass);
    }

    public List<PolicyRule> getAllPolicyRules() {
        return policyDefinition.getAllPolicyRules();
    }

    public PolicyRule getPolicyRule(String policyKey) {
        return policyDefinition.getPolicyRule(policyKey);
    }
}
