package com.zjj.autoconfigure.component.security.abac;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月13日 21:55
 */
public interface AbacCacheManage {


    PolicyRule getRule(String key, String typeClass);


    void putRule(String key, String typeClass, PolicyRule rule);
}
