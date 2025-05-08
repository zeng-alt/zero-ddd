package com.zjj.security.abac.component.supper;

import com.zjj.autoconfigure.component.security.abac.PolicyRule;
import com.zjj.autoconfigure.component.security.abac.ObjectAttribute;
import io.vavr.Tuple2;
import lombok.Getter;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.MethodClassKey;
import org.springframework.lang.NonNull;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月13日 21:21
 */
public abstract class AbstractAbacExpressionAttributeRegistry<T extends PolicyRule> {

    @Getter
    private final MethodSecurityExpressionHandler expressionHandler;
    private final Map<MethodClassKey, Tuple2<String, String>> cachedRoleKeys = new ConcurrentHashMap<>();
    private final Map<String, List<ObjectAttribute>> objectAttributeMap = new ConcurrentHashMap<>();


    protected AbstractAbacExpressionAttributeRegistry(MethodSecurityExpressionHandler expressionHandler, ObjectProvider<ObjectAttribute> objectAttributes) {
        Assert.notNull(expressionHandler, "expressionHandler cannot be null");
        this.expressionHandler = expressionHandler;
    }

    public final T getPolicyRule(Supplier<Authentication> authentication, MethodInvocation mi) {
        Method method = mi.getMethod();
        Object target = mi.getThis();
        Class<?> targetClass = (target != null) ? target.getClass() : null;
        return getPolicyRule(authentication, method, targetClass);
    }

    public final Map<String, Object> getObjectAttribute(MethodInvocation mi) {
        Method method = mi.getMethod();
        Object target = mi.getThis();
        Class<?> targetClass = (target != null) ? target.getClass() : null;
        MethodClassKey cacheKey = new MethodClassKey(method, targetClass);
        Tuple2<String, String> tuple = this.cachedRoleKeys.computeIfAbsent(cacheKey, k -> resolvePolicyKey(method, targetClass));

        return null;
//        if (!objectAttributeMap.containsKey(tuple._1)) {
//            return Flux.empty();
//        }
//
//        return Flux.create(sink -> {
//            List<ObjectAttribute> objectAttributes = objectAttributeMap.get(tuple._1);
//            for (ObjectAttribute objectAttribute : objectAttributes) {
//                sink.next(new Tuple2<>(objectAttribute.getPolicyKey(), objectAttribute.getObject()));
//            }
//            sink.complete();
//        });
    }

//    public final Flux<Tuple2<String, Object>> getObjectAttribute(MethodInvocation mi) {
//        Method method = mi.getMethod();
//        Object target = mi.getThis();
//        Class<?> targetClass = (target != null) ? target.getClass() : null;
//        MethodClassKey cacheKey = new MethodClassKey(method, targetClass);
//        Tuple2<String, String> tuple = this.cachedRoleKeys.computeIfAbsent(cacheKey, k -> resolvePolicyKey(method, targetClass));
//        if (!objectAttributeMap.containsKey(tuple._1)) {
//            return Flux.empty();
//        }
//
//        return Flux.create(sink -> {
//            List<ObjectAttribute> objectAttributes = objectAttributeMap.get(tuple._1);
//            for (ObjectAttribute objectAttribute : objectAttributes) {
//                sink.next(new Tuple2<>(objectAttribute.getPolicyKey(), objectAttribute.getObject()));
//            }
//            sink.complete();
//        });
//    }

    private final T getPolicyRule(Supplier<Authentication> authentication, Method method, Class<?> targetClass) {
        MethodClassKey cacheKey = new MethodClassKey(method, targetClass);
        Tuple2<String, String> tuple = this.cachedRoleKeys.computeIfAbsent(cacheKey, k -> resolvePolicyKey(method, targetClass));
        return resolvePolicyRule(authentication, tuple._1, tuple._2);
    }

    protected abstract T resolvePolicyRule(Supplier<Authentication> authentication, String policyKey, String resourceType);

    @NonNull
    protected abstract Tuple2<String, String> resolvePolicyKey(Method method, Class<?> targetClass);

    public Class<?> targetClass(Method method, Class<?> targetClass) {
        return (targetClass != null) ? targetClass : method.getDeclaringClass();
    }
}
