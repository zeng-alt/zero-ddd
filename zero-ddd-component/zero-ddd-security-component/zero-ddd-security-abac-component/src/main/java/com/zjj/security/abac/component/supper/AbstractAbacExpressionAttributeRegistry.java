package com.zjj.security.abac.component.supper;

import com.zjj.autoconfigure.component.security.abac.PolicyRule;
import io.vavr.Tuple2;
import lombok.Getter;
import lombok.Setter;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.MethodClassKey;
import org.springframework.lang.NonNull;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月13日 21:21
 */
public abstract class AbstractAbacExpressionAttributeRegistry<T extends PolicyRule> {

    @Getter
    private final MethodSecurityExpressionHandler expressionHandler;
    private final Map<MethodClassKey, Tuple2<String, String>> cachedRoleKeys = new ConcurrentHashMap<>();


    protected AbstractAbacExpressionAttributeRegistry(MethodSecurityExpressionHandler expressionHandler) {
        Assert.notNull(expressionHandler, "expressionHandler cannot be null");
        this.expressionHandler = expressionHandler;
    }

    public final T getPolicyRule(MethodInvocation mi) {
        Method method = mi.getMethod();
        Object target = mi.getThis();
        Class<?> targetClass = (target != null) ? target.getClass() : null;
        return getPolicyRule(method, targetClass);
    }

    private final T getPolicyRule(Method method, Class<?> targetClass) {
        MethodClassKey cacheKey = new MethodClassKey(method, targetClass);
        Tuple2<String, String> tuple = this.cachedRoleKeys.computeIfAbsent(cacheKey, k -> resolvePolicyKey(method, targetClass));
        return resolvePolicyRule(tuple._1, tuple._2);
    }

    protected abstract T resolvePolicyRule(String policyKey, String resourceType);

    @NonNull
    protected abstract Tuple2<String, String> resolvePolicyKey(Method method, Class<?> targetClass);

    public Class<?> targetClass(Method method, Class<?> targetClass) {
        return (targetClass != null) ? targetClass : method.getDeclaringClass();
    }
}
