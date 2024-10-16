package com.zjj.bean.componenet;

import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * 要使用反射创建对象性能较差，优其是创建List对象，无法批量创建，对于copyList对象请使用MapStruct<br>
 * 直接使用spring或apache的BeanUtils拷贝List会不生效
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月15日 20:52
 */
public class BeanHelper {

    private BeanHelper() {}


    public static <T> T copyToObject(Object source, Class<T> targetClz, Class<?> editable) {
        T target = BeanUtils.instantiateClass(targetClz);
        BeanUtils.copyProperties(source, target, editable);
        return target;
    }

    public static <T>  T copyToObject(Object source, Class<T> targetClz, String... ignoreProperties) {
        T target = BeanUtils.instantiateClass(targetClz);
        BeanUtils.copyProperties(source, target, ignoreProperties);
        return target;
    }

    public static <T>  T copyToObject(Object source, Class<T> targetClz) {
        T target = BeanUtils.instantiateClass(targetClz);
        BeanUtils.copyProperties(source, target);
        return target;
    }

    public static <S, T> List<T> copyToList(List<S> source, Class<T> targetClz) {
        return source.stream().map(s -> copyToObject(s, targetClz)).toList();
    }

    public static <S, T> List<T> copyToList(List<S> source, Class<T> targetClz, Class<?> editable) {
        return source.stream().map(s -> copyToObject(s, targetClz, editable)).toList();
    }

    public static <S, T> List<T> copyToList(List<S> source, Class<T> targetClz, String... ignoreProperties) {
        return source.stream().map(s -> copyToObject(s, targetClz, ignoreProperties)).toList();
    }
}
