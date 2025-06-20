package com.zjj.cache.component.supper;

import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.autoconfigure.component.security.abac.AbacCacheManage;
import com.zjj.autoconfigure.component.security.abac.PolicyRule;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author zengJiaJun
 * @crateTime 2024年12月14日 21:40
 * @version 1.0
 */
@RequiredArgsConstructor
public class RedisAbacCacheManage implements AbacCacheManage {

    private final RedisStringRepository redisStringRepository;

    public PolicyRule getPolicyRule(String tenant, String key, String typeClass, boolean isPreAuth) {
        return null;
    }

    @Override
    public PolicyRule getPolicyRule(String tenant, String key, boolean isPreAuth) {
        return null;
    }

    @Override
    public PolicyRule getPolicyRule(String key, boolean isPreAuth) {
        key = ABAC_CACHE_KEY + getTenantKey() + (isPreAuth ? "preAuth:" : "postAuth:") + key;
        return redisStringRepository.get(key);
    }

    @Override
    public void putRule(String key, String typeClass, PolicyRule rule) {

    }

    @Override
    public void putRule(String key, PolicyRule rule, boolean isPreAuth) {
        key = ABAC_CACHE_KEY + getTenantKey() + (isPreAuth ? "preAuth:" : "postAuth:") + key;
        redisStringRepository.put(key, rule);
    }


    @Override
    public void deleteRule(String key, boolean isPreAuth) {
        key = ABAC_CACHE_KEY + getTenantKey() + (isPreAuth ? "preAuth:" : "postAuth:") + key;
        redisStringRepository.remove(key);
    }

    @Override
    public void batchPutRule(Map<String, PolicyRule> map, boolean isPreAuth) {
        String preKey = ABAC_CACHE_KEY + getTenantKey() + (isPreAuth ? "preAuth:" : "postAuth:");
        redisStringRepository.batchPut(preKey, map);
    }

    @Override
    public void clear() {
        String preKey = ABAC_CACHE_KEY + getTenantKey();
        redisStringRepository.removeAll(preKey);
    }

    @Override
    public List<PolicyRule> getAllPolicyRules() {
        return null;
    }

    @Override
    public PolicyRule getPolicyRule(String policyKey) {
        return null;
    }

}
