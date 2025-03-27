package com.zjj.bean.componenet;

import com.zjj.autoconfigure.component.UtilException;
import io.vavr.control.Option;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 要使用BeanHelper创建对象性能较差，优其是创建List对象，无法批量创建，对于copyList对象请使用MapStruct<br>
 * 直接使用spring或apache的BeanUtils拷贝List会不生效
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月15日 20:52
 */
public class BeanHelper extends BeanUtils {

    private BeanHelper() {}

    public static void copyPropertiesIgnoringNull(Object source, Object target) {
        PropertyDescriptor[] targetDescriptors = BeanUtils.getPropertyDescriptors(target.getClass());

        for (PropertyDescriptor targetDescriptor : targetDescriptors) {
            String propertyName = targetDescriptor.getName();
            // Skip the property if it's a getter or setter method
            if (targetDescriptor.getReadMethod() == null || targetDescriptor.getWriteMethod() == null) {
                continue;
            }

            try {
                // Get the getter method of the source
                Method readMethod = targetDescriptor.getReadMethod();
                Object value = readMethod.invoke(source);

                // Only copy if the value is not null
                if (value != null && !(value instanceof Optional<?>) && !(value instanceof Collection<?>)) {
                    // Get the setter method of the target
                    Method writeMethod = targetDescriptor.getWriteMethod();
                    writeMethod.invoke(target, value);
                }
            } catch (Exception e) {
                throw new UtilException(e);
            }
        }
    }

    @NonNull
    public static <S, T> Option<T> copyToOptionObject(@NonNull Optional<S> source, Class<T> targetClz, BiConsumer<S, T> consumer) {
        return source.map(s -> Option.of(copyToObject(s, targetClz, consumer))).orElseGet(Option::none);
    }

    @NonNull
    public static <S, T> Option<T> copyToOptionObject(@NonNull Option<S> source, Class<T> targetClz, BiConsumer<S, T> consumer) {
        return source.map(s -> Option.of(copyToObject(s, targetClz, consumer))).getOrElse(Option::none);
    }

    @NonNull
    public static <S, T> Option<T> copyToOptionObject(S source, Class<T> targetClz, BiConsumer<S, T> consumer) {
        if (source == null) return Option.none();
        return Option.of(copyToObject(source, targetClz, consumer));
    }

    @NonNull
    public static <S, T> Option<T> copyToOptionObject(@NonNull Optional<S> source, Class<T> targetClz, Consumer<T> consumer) {
        return source.map(s -> Option.of(copyToObject(s, targetClz, consumer))).orElseGet(Option::none);
    }

    @NonNull
    public static <S, T> Option<T> copyToOptionObject(@NonNull Option<S> source, Class<T> targetClz, Consumer<T> consumer) {
        return source.map(s -> Option.of(copyToObject(s, targetClz, consumer))).getOrElse(Option::none);
    }

    @NonNull
    public static <S, T> Option<T> copyToOptionObject(S source, Class<T> targetClz, Consumer<T> consumer) {
        if (source == null) return Option.none();
        return Option.of(copyToObject(source, targetClz, consumer));
    }


    @NonNull
    public static <S, T> Option<T> copyToOptionObject(@NonNull Optional<S> source, Class<T> targetClz) {
        return source.map(s -> Option.of(copyToObject(s, targetClz))).orElseGet(Option::none);
    }

    @NonNull
    public static <S, T> Option<T> copyToOptionObject(@NonNull Option<S> source, Class<T> targetClz) {
        return source.map(s -> Option.of(copyToObject(s, targetClz))).getOrElse(Option::none);
    }

    @NonNull
    public static <S, T> Option<T> copyToOptionObject(S source, Class<T> targetClz) {
        if (source == null) return Option.none();
        return Option.of(copyToObject(source, targetClz));
    }


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
    public static <S, T>  T copyToObject(S source, Class<T> targetClz) {
        T target = BeanUtils.instantiateClass(targetClz);
        BeanUtils.copyProperties(source, target);
        return target;
    }

    public static <S, T>  T copyToObject(S source, Class<T> targetClz, Consumer<T> consumer) {
        T target = BeanUtils.instantiateClass(targetClz);
        BeanUtils.copyProperties(source, target);
        if (consumer != null) {
            consumer.accept(target);
        }
        return target;
    }


    public static <S, T>  T copyToObject(S source, Class<T> targetClz, BiConsumer<S, T> consumer) {
        T target = BeanUtils.instantiateClass(targetClz);
        BeanUtils.copyProperties(source, target);
        if (consumer != null) {
            consumer.accept(source, target);
        }
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
