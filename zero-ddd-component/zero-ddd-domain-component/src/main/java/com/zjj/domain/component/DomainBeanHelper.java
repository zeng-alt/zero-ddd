package com.zjj.domain.component;

import com.zjj.autoconfigure.component.UtilException;
import io.vavr.control.Option;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.jmolecules.ddd.types.Identifiable;
import org.jmolecules.ddd.types.Identifier;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Persistable;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年01月09日 16:44
 */
public class DomainBeanHelper {

    private DomainBeanHelper() {}


    @NonNull
    public static <S extends Persistable<?>, T extends Identifiable<?>, ID extends Identifier> Option<T> copyToOptionDomain(@NonNull Optional<S> source, Class<T> targetClz, Class<ID> idClass, BiConsumer<S, T> consumer) {
        return source.map(s -> Option.of(copyToDomain(s, targetClz, idClass, consumer))).orElseGet(Option::none);
    }

    @NonNull
    public static <S extends Persistable<?>, T extends Identifiable<?>, ID extends Identifier> Option<T> copyToOptionDomain(@NonNull Option<S> source, Class<T> targetClz, Class<ID> idClass, BiConsumer<S, T> consumer) {
        return source.map(s -> Option.of(copyToDomain(s, targetClz, idClass, consumer))).getOrElse(Option::none);
    }

    @NonNull
    public static <S extends Persistable<?>, T extends Identifiable<?>, ID extends Identifier> Option<T> copyToOptionDomain(S source, Class<T> targetClz, Class<ID> idClass, BiConsumer<S, T> consumer) {
        if (source == null) return Option.none();
        return Option.of(copyToDomain(source, targetClz, idClass, consumer));
    }

    @NonNull
    public static <S extends Persistable<?>, T extends Identifiable<?>, ID extends Identifier> Option<T> copyToOptionDomain(@NonNull Optional<S> source, Class<T> targetClz, Class<ID> idClass, Consumer<T> consumer) {
        return source.map(s -> Option.of(copyToDomain(s, targetClz, idClass, consumer))).orElseGet(Option::none);
    }

    @NonNull
    public static <S extends Persistable<?>, T extends Identifiable<?>, ID extends Identifier> Option<T> copyToOptionDomain(@NonNull Option<S> source, Class<T> targetClz, Class<ID> idClass, Consumer<T> consumer) {
        return source.map(s -> Option.of(copyToDomain(s, targetClz, idClass, consumer))).getOrElse(Option::none);
    }

    @NonNull
    public static <S extends Persistable<?>, T extends Identifiable<?>, ID extends Identifier> Option<T> copyToOptionDomain(S source, Class<T> targetClz, Class<ID> idClass, Consumer<T> consumer) {
        if (source == null) return Option.none();
        return Option.of(copyToDomain(source, targetClz, idClass, consumer));
    }


    @NonNull
    public static <S extends Persistable<?>, T extends Identifiable<?>, ID extends Identifier> Option<T> copyToOptionDomain(@NonNull Optional<S> source, Class<T> targetClz, Class<ID> idClass) {
        return source.map(s -> Option.of(copyToDomain(s, targetClz, idClass))).orElseGet(Option::none);
    }

    @NonNull
    public static <S extends Persistable<?>, T extends Identifiable<?>, ID extends Identifier> Option<T> copyToOptionDomain(@NonNull Option<S> source, Class<T> targetClz, Class<ID> idClass) {
        return source.map(s -> Option.of(copyToDomain(s, targetClz, idClass))).getOrElse(Option::none);
    }

    @NonNull
    public static <S extends Persistable<?>, T extends Identifiable<?>, ID extends Identifier> Option<T> copyToOptionDomain(S source, Class<T> targetClz, Class<ID> idClass) {
        if (source == null) return Option.none();
        return Option.of(copyToDomain(source, targetClz, idClass));
    }



    @NonNull
    public static <S extends Identifiable<?>, T extends Persistable<?>> Option<T> copyToOptionDomain(@NonNull Optional<S> source, Class<T> targetClz, BiConsumer<S, T> consumer) {
        return source.map(s -> Option.of(copyToDomain(s, targetClz, consumer))).orElseGet(Option::none);
    }

    @NonNull
    public static <S extends Identifiable<?>, T extends Persistable<?>> Option<T> copyToOptionDomain(@NonNull Option<S> source, Class<T> targetClz, BiConsumer<S, T> consumer) {
        return source.map(s -> Option.of(copyToDomain(s, targetClz, consumer))).getOrElse(Option::none);
    }

    @NonNull
    public static <S extends Identifiable<?>, T extends Persistable<?>> Option<T> copyToOptionDomain(S source, Class<T> targetClz, BiConsumer<S, T> consumer) {
        if (source == null) return Option.none();
        return Option.of(copyToDomain(source, targetClz, consumer));
    }

    @NonNull
    public static <S extends Identifiable<?>, T extends Persistable<?>> Option<T> copyToOptionDomain(@NonNull Optional<S> source, Class<T> targetClz, Consumer<T> consumer) {
        return source.map(s -> Option.of(copyToDomain(s, targetClz, consumer))).orElseGet(Option::none);
    }

    @NonNull
    public static <S extends Identifiable<?>, T extends Persistable<?>> Option<T> copyToOptionDomain(@NonNull Option<S> source, Class<T> targetClz, Consumer<T> consumer) {
        return source.map(s -> Option.of(copyToDomain(s, targetClz, consumer))).getOrElse(Option::none);
    }

    @NonNull
    public static <S extends Identifiable<?>, T extends Persistable<?>> Option<T> copyToOptionDomain(S source, Class<T> targetClz, Consumer<T> consumer) {
        if (source == null) return Option.none();
        return Option.of(copyToDomain(source, targetClz, consumer));
    }


    @NonNull
    public static <S extends Identifiable<?>, T extends Persistable<?>> Option<T> copyToOptionDomain(@NonNull Optional<S> source, Class<T> targetClz) {
        return source.map(s -> Option.of(copyToDomain(s, targetClz))).orElseGet(Option::none);
    }

    @NonNull
    public static <S extends Identifiable<?>, T extends Persistable<?>> Option<T> copyToOptionDomain(@NonNull Option<S> source, Class<T> targetClz) {
        return source.map(s -> Option.of(copyToDomain(s, targetClz))).getOrElse(Option::none);
    }

    @NonNull
    public static <S extends Identifiable<?>, T extends Persistable<?>> Option<T> copyToOptionDomain(S source, Class<T> targetClz) {
        if (source == null) return Option.none();
        return Option.of(copyToDomain(source, targetClz));
    }

    private static <S extends Identifiable<?>, T extends Persistable<?>> void copyId(S s, T t) {
        Object sourceId = s.getId();
        if (sourceId != null) {
            BeanUtils.copyProperties(sourceId, t);
        }
    }

    private static <S extends Persistable<?>, T extends Identifiable<?>, ID extends Identifier> void copyId(S s, T t, Class<ID> idClass) {
        Object sourceId = s.getId();
        try {
            ID id = (ID) MethodUtils.invokeStaticMethod(idClass, "of", sourceId);
            MethodUtils.invokeMethod(t, "setId", id);
        } catch (Exception e) {
            throw new UtilException(e);
        }
    }


    /**
     * targetClz不支持record类
     */
    public static <S extends Identifiable<?>, T extends Persistable<?>> T copyToDomain(S source, Class<T> targetClz) {
        T target = BeanUtils.instantiateClass(targetClz);
        BeanUtils.copyProperties(source, target);
        copyId(source, target);
        return target;
    }

    public static <S extends Persistable<?>, T extends Identifiable<?>, ID extends Identifier>  T copyToDomain(S source, Class<T> targetClz, Class<ID> idClass) {
        T target = BeanUtils.instantiateClass(targetClz);
        BeanUtils.copyProperties(source, target);
        copyId(source, target, idClass);
        return target;
    }

    /**
     * targetClz不支持record类
     */
    public static <S extends Persistable<?>, T extends Identifiable<?>, ID extends Identifier> T copyToDomain(S source, Class<T> targetClz, Class<ID> idClass, String... ignoreProperties) {
        T target = BeanUtils.instantiateClass(targetClz);
        BeanUtils.copyProperties(source, target, ignoreProperties);
        copyId(source, target, idClass);
        return target;
    }

    public static <S extends Identifiable<?>, T extends Persistable<?>> T copyToDomain(S source, Class<T> targetClz, String... ignoreProperties) {
        T target = BeanUtils.instantiateClass(targetClz);
        BeanUtils.copyProperties(source, target, ignoreProperties);
        copyId(source, target);
        return target;
    }

    public static <S extends Persistable<?>, T extends Identifiable<?>, ID extends Identifier> T copyToDomain(S source, Class<T> targetClz, Class<ID> idClass, Consumer<T> consumer) {
        T target = BeanUtils.instantiateClass(targetClz);
        BeanUtils.copyProperties(source, target);
        if (consumer != null) {
            consumer.accept(target);
        }
        copyId(source, target, idClass);
        return target;
    }

    public static <S extends Identifiable<?>, T extends Persistable<?>> T copyToDomain(S source, Class<T> targetClz, Consumer<T> consumer) {
        T target = BeanUtils.instantiateClass(targetClz);
        BeanUtils.copyProperties(source, target);
        if (consumer != null) {
            consumer.accept(target);
        }
        copyId(source, target);
        return target;
    }


    public static <S extends Persistable<?>, T extends Identifiable<?>, ID extends Identifier>  T copyToDomain(S source, Class<T> targetClz, Class<ID> idClass, BiConsumer<S, T> consumer) {
        T target = BeanUtils.instantiateClass(targetClz);
        BeanUtils.copyProperties(source, target);
        if (consumer != null) {
            consumer.accept(source, target);
        }
        copyId(source, target, idClass);
        return target;
    }

    public static <S extends Identifiable<?>, T extends Persistable<?>> T copyToDomain(S source, Class<T> targetClz, BiConsumer<S, T> consumer) {
        T target = BeanUtils.instantiateClass(targetClz);
        BeanUtils.copyProperties(source, target);
        if (consumer != null) {
            consumer.accept(source, target);
        }
        copyId(source, target);
        return target;
    }

    /**
     * targetClz不支持record类
     */
    public static <S extends Persistable<?>, T extends Identifiable<?>, ID extends Identifier> List<T> copyToList(List<S> source, Class<T> targetClz, Class<ID> idClass) {
        return copyToList(source, targetClz, idClass, (Class<?>) null);
    }

    public static <S extends Identifiable<?>, T extends Persistable<?>> List<T> copyToList(List<S> source, Class<T> targetClz) {
        ArrayList<T> result = new ArrayList<>();
        Constructor<T> constructor = findConstructor(targetClz);

        for (S s : source) {
            T t = BeanUtils.instantiateClass(constructor);
            // For regular classes, use BeanUtils.copyProperties
            BeanUtils.copyProperties(s, t);
            copyId(s, t);
            result.add(t);
        }

        return result;
    }

    public static <S extends Persistable<?>, T extends Identifiable<?>, ID extends Identifier> List<T> copyToList(List<S> source, Class<T> targetClz, Class<ID> idclass, Class<?> editable) {
        ArrayList<T> result = new ArrayList<>();
        Constructor<T> constructor = findConstructor(targetClz);

        for (S s : source) {
            T t = BeanUtils.instantiateClass(constructor);
            // For regular classes, use BeanUtils.copyProperties
            BeanUtils.copyProperties(s, t, editable);
            copyId(s, t, idclass);
            result.add(t);
        }

        return result;
    }

//    public static <S extends Identifiable<?>, T extends Persistable<?>> List<T> copyToList(List<S> source, Class<T> targetClz, Class<?> editable) {
//        ArrayList<T> result = new ArrayList<>();
//        Constructor<T> constructor = findConstructor(targetClz);
//
//        for (S s : source) {
//            T t = BeanUtils.instantiateClass(constructor);
//            // For regular classes, use BeanUtils.copyProperties
//            BeanUtils.copyProperties(s, t, editable);
//            copyId(s, t);
//            result.add(t);
//        }
//
//        return result;
//    }

    public static <S extends Persistable<?>, T extends Identifiable<?>, ID extends Identifier> List<T> copyToList(List<S> source, Class<T> targetClz, Class<ID> idClass, String... ignoreProperties) {
        ArrayList<T> result = new ArrayList<>();
        Constructor<T> constructor = findConstructor(targetClz);

        for (S s : source) {
            T t = BeanUtils.instantiateClass(constructor);
            // For regular classes, use BeanUtils.copyProperties
            BeanUtils.copyProperties(s, t, ignoreProperties);
            copyId(s, t, idClass);
            result.add(t);
        }

        return result;
    }

    public static <S extends Identifiable<?>, T extends Persistable<?>> List<T> copyToList(List<S> source, Class<T> targetClz, String... ignoreProperties) {
        ArrayList<T> result = new ArrayList<>();
        Constructor<T> constructor = findConstructor(targetClz);

        for (S s : source) {
            T t = BeanUtils.instantiateClass(constructor);
            // For regular classes, use BeanUtils.copyProperties
            BeanUtils.copyProperties(s, t, ignoreProperties);
            copyId(s, t);
            result.add(t);
        }

        return result;
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
}
