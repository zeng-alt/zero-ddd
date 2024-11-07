package com.zjj.bean.componenet;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * 要使用BeanHelper创建对象性能较差，优其是创建List对象，无法批量创建，对于copyList对象请使用MapStruct<br>
 * 直接使用spring或apache的BeanUtils拷贝List会不生效
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月15日 20:52
 */
public class BeanHelper {

    private BeanHelper() {}


    /**
     * targetClz不支持record类
     */
    public static <T> T copyToObject(Object source, Class<T> targetClz, Class<?> editable) {
        T target = BeanUtils.instantiateClass(targetClz);
        BeanUtils.copyProperties(source, target, editable);
        return target;
    }

    /**
     * targetClz不支持record类
     */
    public static <T>  T copyToObject(Object source, Class<T> targetClz, String... ignoreProperties) {
        T target = BeanUtils.instantiateClass(targetClz);
        BeanUtils.copyProperties(source, target, ignoreProperties);
        return target;
    }

    /**
     * targetClz不支持record类
     */
    public static <T>  T copyToObject(Object source, Class<T> targetClz) {
        T target = BeanUtils.instantiateClass(targetClz);
        BeanUtils.copyProperties(source, target);
        return target;
    }

    private static <T> Constructor<T> findConstructor(Class<T> targetClz) {
        Assert.notNull(targetClz, "Class must not be null");
        if (targetClz.isInterface()) {
            throw new BeanInstantiationException(targetClz, "Specified class is an interface");
        }
        Constructor<T> ctor;
        try {
            ctor = targetClz.getDeclaredConstructor();
        }
        catch (NoSuchMethodException ex) {
            ctor = BeanUtils.findPrimaryConstructor(targetClz);
            if (ctor == null) {
                throw new BeanInstantiationException(targetClz, "No default constructor found", ex);
            }
        }
        catch (LinkageError err) {
            throw new BeanInstantiationException(targetClz, "Unresolvable class definition", err);
        }
        return ctor;
    }

    /**
     * targetClz不支持record类
     */
    public static <S, T> List<T> copyToList(List<S> source, Class<T> targetClz) {
        return copyToList(source, targetClz, (Class<?>) null);
    }

    public static <S, T> List<T> copyToList(List<S> source, Class<T> targetClz, Class<?> editable) {
        ArrayList<T> result = new ArrayList<>();
        Constructor<T> constructor = findConstructor(targetClz);

        for (S s : source) {
            T t = BeanUtils.instantiateClass(constructor);
            // For regular classes, use BeanUtils.copyProperties
            BeanUtils.copyProperties(s, t, editable);
            result.add(t);
        }

        return result;
    }

    public static <S, T> List<T> copyToList(List<S> source, Class<T> targetClz, String... ignoreProperties) {
        ArrayList<T> result = new ArrayList<>();
        Constructor<T> constructor = findConstructor(targetClz);

        for (S s : source) {
            T t = BeanUtils.instantiateClass(constructor);
            // For regular classes, use BeanUtils.copyProperties
            BeanUtils.copyProperties(s, t, ignoreProperties);
            result.add(t);
        }

        return result;
    }

}
