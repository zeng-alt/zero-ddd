package com.zjj.security.abac.component.supper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.*;
import org.springframework.security.access.expression.SecurityExpressionOperations;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月17日 21:22
 */
@Slf4j
public class RootMethodResolver implements MethodResolver {

    private final Map<MethodKey, MethodExecutor> methodMap = new ConcurrentHashMap<>();

    public RootMethodResolver() {
        Class<SecurityExpressionOperations> securityExpressionOperationsClass = SecurityExpressionOperations.class;
        try {
            final Method hasAuthority = securityExpressionOperationsClass.getMethod("hasAuthority", String.class);
            methodMap.put(new MethodKey("hasAuthority", 1), (context, target, arguments) -> {
                try {
                    return new TypedValue(hasAuthority.invoke(target));
                } catch (Exception e) {
                    throw new AccessException("Error invoking method", e);
                }
            });

            final Method hasAnyAuthority1 = securityExpressionOperationsClass.getMethod("hasAnyAuthority", String[].class);
            methodMap.put(new MethodKey("hasAnyAuthority", -1), (context, target, arguments) -> {
                try {
                    return new TypedValue(hasAnyAuthority1.invoke(target, arguments));
                } catch (Exception e) {
                    throw new AccessException("Error invoking method", e);
                }
            });

            final Method hasRole = securityExpressionOperationsClass.getMethod("hasRole", String.class);
            methodMap.put(new MethodKey("hasRole", 1), (context, target, arguments) -> {
                try {
                    return new TypedValue(hasRole.invoke(target, arguments[0]));
                } catch (Exception e) {
                    throw new AccessException("Error invoking method", e);
                }
            });

            final Method hasAnyRole = securityExpressionOperationsClass.getMethod("hasAnyRole", String[].class);
            methodMap.put(new MethodKey("hasAnyRole", -1), (context, target, arguments) -> {
                try {
                    return new TypedValue(hasAnyRole.invoke(target, arguments));
                } catch (Exception e) {
                    throw new AccessException("Error invoking method", e);
                }
            });

            final Method permitAll = securityExpressionOperationsClass.getMethod("permitAll");
            methodMap.put(new MethodKey("permitAll", 0), (context, target, arguments) -> {
                try {
                    return new TypedValue(permitAll.invoke(target));
                } catch (Exception e) {
                    throw new AccessException("Error invoking method", e);
                }
            });

            final Method denyAll = securityExpressionOperationsClass.getMethod("denyAll");
            methodMap.put(new MethodKey("denyAll", 0), (context, target, arguments) -> {
                try {
                    return new TypedValue(denyAll.invoke(target));
                } catch (Exception e) {
                    throw new AccessException("Error invoking method", e);
                }
            });

            final Method isAnonymous = securityExpressionOperationsClass.getMethod("isAnonymous");
            methodMap.put(new MethodKey("isAnonymous", 0), (context, target, arguments) -> {
                try {
                    return new TypedValue(isAnonymous.invoke(target));
                } catch (Exception e) {
                    throw new AccessException("Error invoking method", e);
                }
            });

            final Method isRememberMe = securityExpressionOperationsClass.getMethod("isRememberMe");
            methodMap.put(new MethodKey("isRememberMe", 0), (context, target, arguments) -> {
                try {
                    return new TypedValue(isRememberMe.invoke(target));
                } catch (Exception e) {
                    throw new AccessException("Error invoking method", e);
                }
            });

            final Method isFullyAuthenticated = securityExpressionOperationsClass.getMethod("isFullyAuthenticated");
            methodMap.put(new MethodKey("isFullyAuthenticated", 0), (context, target, arguments) -> {
                try {
                    return new TypedValue(isFullyAuthenticated.invoke(target));
                } catch (Exception e) {
                    throw new AccessException("Error invoking method", e);
                }
            });

            final Method hasPermission = securityExpressionOperationsClass.getMethod("hasPermission", Object.class, Object.class);
            methodMap.put(new MethodKey("hasPermission", 2), (context, target, arguments) -> {
                try {
                    return new TypedValue(hasPermission.invoke(target, arguments[0], arguments[1]));
                } catch (Exception e) {
                    throw new AccessException("Error invoking method", e);
                }
            });

            final Method hasPermission1 = securityExpressionOperationsClass.getMethod("hasPermission", Object.class, String.class, Object.class);
            methodMap.put(new MethodKey("hasPermission", 3), (context, target, arguments) -> {
                try {
                    return new TypedValue(hasPermission1.invoke(target, arguments[0], arguments[1], arguments[2]));
                } catch (Exception e) {
                    throw new AccessException("Error invoking method", e);
                }
            });

            Class<AbacSecurityExpressionRoot> rootClass = AbacSecurityExpressionRoot.class;
            final Method size = rootClass.getMethod("size", Object.class);
            methodMap.put(new MethodKey("size", 1), (context, target, arguments) -> {
                try {
                    return new TypedValue(size.invoke(target, arguments[0]));
                } catch (Exception e) {
                    throw new AccessException("Error invoking method", e);
                }
            });

            final Method anyMatch = rootClass.getMethod("anyMatch", Object.class, String.class, Object.class);
            methodMap.put(new MethodKey("anyMatch", 3), (context, target, arguments) -> {
                try {
                    return new TypedValue(anyMatch.invoke(target, arguments[0], arguments[1], arguments[2]));
                } catch (Exception e) {
                    throw new AccessException("Error invoking method", e);
                }
            });

            final Method anyMatch1 = rootClass.getMethod("anyMatch", Object.class, Object.class);
            methodMap.put(new MethodKey("anyMatch", 2), (context, target, arguments) -> {
                try {
                    return new TypedValue(anyMatch1.invoke(target, arguments[0], arguments[1]));
                } catch (Exception e) {
                    throw new AccessException("Error invoking method", e);
                }
            });

            final Method allMatch = rootClass.getMethod("allMatch", Object.class, String.class, Object.class);
            methodMap.put(new MethodKey("allMatch", 3), (context, target, arguments) -> {
                try {
                    return new TypedValue(allMatch.invoke(target, arguments[0], arguments[1], arguments[2]));
                } catch (Exception e) {
                    throw new AccessException("Error invoking method", e);
                }
            });

            final Method allMatch1 = rootClass.getMethod("allMatch", Object.class, Object.class);
            methodMap.put(new MethodKey("allMatch", 2), (context, target, arguments) -> {
                try {
                    return new TypedValue(allMatch1.invoke(target, arguments[0], arguments[1]));
                } catch (Exception e) {
                    throw new AccessException("Error invoking method", e);
                }
            });

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MethodExecutor resolve(EvaluationContext context, Object targetObject, String name, List<TypeDescriptor> argumentTypes) throws AccessException {
        return methodMap.getOrDefault(new MethodKey(name, argumentTypes.size()), null);
    }

    @RequiredArgsConstructor
    public static class MethodKey {
        private final String name;
        private final Integer num;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MethodKey methodKey = (MethodKey) o;
            return Objects.equals(name, methodKey.name) && (num == -1 || Objects.equals(num, methodKey.num));
        }

        @Override
        public int hashCode() {
            if (num == 1) {
                return Objects.hash(name);
            }
            return Objects.hash(name, num);
        }
    }
}
