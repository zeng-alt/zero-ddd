package com.zjj.autoconfigure.component.security.abac;

import com.zjj.autoconfigure.component.security.abac.AbacCacheManage;
import com.zjj.autoconfigure.component.tenant.TenantKey;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月16日 21:06
 */
public interface PolicyDefinition extends TenantKey {
    public List<PolicyRule> getAllPolicyRules();

    PolicyRule getPolicyRule(String tenant, String key, String typeClass, boolean isPreAuth);
    PolicyRule getPolicyRule(String tenant, String key, boolean isPreAuth);
    PolicyRule getPolicyRule(String key, boolean isPreAuth);

    PolicyRule getPolicyRule(String policyKey);
}
