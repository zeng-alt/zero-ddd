package com.zjj.cache.component.supper;

import com.zjj.autoconfigure.component.security.abac.AbacCacheManage;
import com.zjj.autoconfigure.component.security.abac.PolicyRule;

import java.util.function.Supplier;

/**
 * @author zengJiaJun
 * @crateTime 2024年12月14日 21:40
 * @version 1.0
 */
public class RedisAbacCacheManage implements AbacCacheManage {
    public PolicyRule getRule(String tenant, String key, String typeClass) {
        return null;
    }

    @Override
    public void putRule(String key, String typeClass, PolicyRule rule) {

    }
}
