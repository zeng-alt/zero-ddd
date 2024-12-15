package com.zjj.autoconfigure.component.security.abac;

import org.springframework.security.core.Authentication;

import java.util.function.Supplier;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月13日 21:55
 */
public interface AbacCacheManage {


    PolicyRule getRule(String tenant, String key, String typeClass);


    void putRule(String key, String typeClass, PolicyRule rule);
}
