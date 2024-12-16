package com.zjj.security.abac.component.supper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月16日 11:20
 */
@Getter
@Setter
public class AbacSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {
    private Object filterObject;
    private Map<String, Object> context = new ConcurrentHashMap<>();
    private Object subject;
    private Object returnObject;
    private Object target;

    public AbacSecurityExpressionRoot(Authentication a) {
        super(a);
    }

    public AbacSecurityExpressionRoot(Supplier<Authentication> authentication) {
        super(authentication);
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    public void setThis(Object target) {
        this.target = target;
    }

    public void addContext(Map<String, Object> map) {
        // 过滤value为null或key为null的map
        map.entrySet().removeIf(entry -> entry.getValue() == null || entry.getKey() == null);
        this.context.putAll(map);
    }

    @Override
    public Object getThis() {
        return this.target;
    }
}
