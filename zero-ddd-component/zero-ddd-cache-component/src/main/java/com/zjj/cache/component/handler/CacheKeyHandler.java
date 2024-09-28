package com.zjj.cache.component.handler;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

/**
 * @author zengJiaJun
 * @crateTime 2024年06月16日 21:30
 * @version 1.0
 */
public class CacheKeyHandler implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append(target.getClass().getName());
        sb.append(method.getName());
        for (Object obj : params) {
            sb.append(obj.toString());
        }
        return sb.toString();
    }
}
