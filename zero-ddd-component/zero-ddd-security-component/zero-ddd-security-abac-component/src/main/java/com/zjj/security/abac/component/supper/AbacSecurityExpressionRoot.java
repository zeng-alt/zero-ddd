package com.zjj.security.abac.component.supper;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
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

    private Map<String, Object> context = new ConcurrentHashMap<>();
    private Object subject;
    private Object returnObject;
    @Setter
    private List<Object> target;

    public AbacSecurityExpressionRoot(Authentication a) {
        super(a);
    }

    public AbacSecurityExpressionRoot(Supplier<Authentication> authentication) {
        super(authentication);
    }

    @Override
    public void setFilterObject(Object filterObject) {
    }

    @Override
    public Object getFilterObject() {
        return null;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
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

    public final int size(Object o) {
        if (o == null) {
            return 0;
        }
        if (o instanceof Collection<?> collection) {
            return collection.size();
        } else if (o instanceof Map<?,?> map) {
            return map.size();
        } else if (o.getClass().isArray()) {
            return Array.getLength(o);
        }

        return 1;
    }

    public final boolean anyMatch(Object o, Object value) {
        if (o == null) {
            return false;
        }
        if (o instanceof Collection<?> collection) {
            return collection.stream().anyMatch(item -> Objects.equals(item, value));
        }
        if (o instanceof Map<?,?> map) {
            return map.values().stream().anyMatch(item -> Objects.equals(item, value));
        }
        return Objects.equals(o, value);
    }

    public final boolean anyMatch(Object o, String propertyName, Object value) {
        if (o == null) {
            return false;
        }
        if (o instanceof Collection<?> collection) {
            return collection.stream().anyMatch(item -> Objects.equals(getProperty(item, propertyName), value));
        }
        if (o instanceof Map<?,?> map) {
            return map.values().stream().anyMatch(item -> Objects.equals(getProperty(item, propertyName), value));
        }
        return Objects.equals(getProperty(o, propertyName), value);
    }

    public final boolean allMatch(Object o, Object value) {
        if (o == null) {
            return false;
        }
        if (o instanceof Collection<?> collection) {
            return collection.stream().allMatch(item -> Objects.equals(item, value));
        }
        if (o instanceof Map<?,?> map) {
            return map.values().stream().allMatch(item -> Objects.equals(item, value));
        }
        return Objects.equals(o, value);
    }

    public final boolean allMatch(Object o, String propertyName, Object value) {
        if (o == null) {
            return false;
        }
        if (o instanceof Collection<?> collection) {
            return collection.stream().allMatch(item -> Objects.equals(getProperty(item, propertyName), value));
        }
        if (o instanceof Map<?,?> map) {
            return map.values().stream().allMatch(item -> Objects.equals(getProperty(item, propertyName), value));
        }
        return Objects.equals(getProperty(o, propertyName), value);
    }

    private Object getProperty(Object object, String propertyName) {
        try {
            Method method = BeanUtils.findMethod(object.getClass(), "get" + StringUtils.capitalize(propertyName));
            if (method == null) {
                return null;
            }
            return method.invoke(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
