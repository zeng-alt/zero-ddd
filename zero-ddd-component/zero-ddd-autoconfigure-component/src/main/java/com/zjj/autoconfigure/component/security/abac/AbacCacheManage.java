package com.zjj.autoconfigure.component.security.abac;

import org.springframework.security.core.Authentication;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月13日 21:55
 */
public interface AbacCacheManage extends PolicyDefinition {

    String ABAC_CACHE_KEY = "abac:";


    void putRule(String key, String typeClass, PolicyRule rule);

    void putRule(String key, PolicyRule rule, boolean isPreAuth);

    void deleteRule(String key, boolean isPreAuth);

    void batchPutRule(Map<String, PolicyRule> map, boolean isPreAuth);

    default void putPreAuthRule(String key, PolicyRule rule) {
        this.putRule(key, rule, true);
    }

    default void putPostAuthRule(String key, PolicyRule rule) {
        this.putRule(key, rule, false);
    }

    void clear();
}
