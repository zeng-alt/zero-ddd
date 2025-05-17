package org.springframework.data.querydsl.binding;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CollectionPathBase;
import com.querydsl.core.types.dsl.SimpleExpression;
import org.springframework.beans.*;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.Property;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.data.mapping.PropertyPath;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.binding.PathInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月26日 09:48
 */
public class EntityBuilder {

    private final ConversionService conversionService;
    private final MultiValueBinding<Path<? extends Object>, Object> defaultBinding;
    private final Map<PathInformation, Path<?>> paths;
    private final EntityPathResolver resolver;


    public EntityBuilder(ConversionService conversionService, EntityPathResolver resolver) {

        Assert.notNull(conversionService, "ConversionService must not be null");

        this.defaultBinding = new QuerydslDefaultBinding();
        this.conversionService = conversionService;
        this.paths = new ConcurrentHashMap<>();
        this.resolver = resolver;
    }

    public <T> List<T> getEntityId(TypeInformation<T> type, Map<String, Object> values, QuerydslBindings bindings) {
        Assert.notNull(bindings, "Context must not be null");
        List<T> list = new ArrayList<>();
        Map.Entry<String, Object> entry = values.entrySet().iterator().next();
        String path = entry.getKey();
        if (!bindings.isPathAvailable(path, type)) {
            return list;
        }
        PathInformation propertyPath = bindings.getPropertyPath(path, type);
        if (propertyPath == null) {
            return list;
        }
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll((Collection<?>) entry.getValue());
        Collection<T> value = (Collection<T>) convertToPropertyPathSpecificType(objects, propertyPath);
        list.addAll(value);
        return list;
    }

    public <T> T getEntity(TypeInformation<T> type,
                           MultiValueMap<String, ?> values,
                           QuerydslBindings bindings) {

        Assert.notNull(bindings, "Context must not be null");
        if (values.isEmpty()) {
            throw new RuntimeException("实体属性不能为空");
        }

        // 1. 实例化根实体
        T root = BeanUtils.instantiateClass(type.getType());

        // 2. 收集符合 bindings 的属性
        MutablePropertyValues pvs = new MutablePropertyValues();
        for (var entry : values.entrySet()) {
            String path = entry.getKey();
            if (bindings.isPathAvailable(path, type)) {
                // 直接把 profile.name / other.nested.path 的 key 放进去
                pvs.add(path, entry.getValue().get(0));
            }
        }
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(root);
        // 3. 用 DataBinder 或 BeanWrapperImpl 注入
//        BeanWrapper wrapper = new BeanWrapperImpl(root);
        wrapper.setAutoGrowNestedPaths(true);

        // 可选：如果你在容器里有定制的 ConversionService，可以这样注入
        wrapper.setConversionService(conversionService);


        wrapper.setPropertyValues(pvs);

        return root;
    }



//    public <T> T getEntity(TypeInformation<T> type, MultiValueMap<String, ?> values, QuerydslBindings bindings) {
//
//        Assert.notNull(bindings, "Context must not be null");
//
//        BooleanBuilder builder = new BooleanBuilder();
//        T t = BeanUtils.instantiateClass(type.getType());
//        String name = type.getType().getName();
//        Map<String, PropertyDescriptor> propertyMap = Arrays.stream(BeanUtils.getPropertyDescriptors(t.getClass())).collect(Collectors.toMap(p -> p.getName(), e -> e));
//        if (values.isEmpty()) {
//            throw new RuntimeException("实体属性不能为空");
//        }
//
//        for (var entry : values.entrySet()) {
//
//            if (isSingleElementCollectionWithEmptyItem(entry.getValue())) {
//                continue;
//            }
//
//            String path = entry.getKey();
//
//            if (!bindings.isPathAvailable(path, type)) {
//                continue;
//            }
//
//            PathInformation propertyPath = bindings.getPropertyPath(path, type);
//
//            if (propertyPath == null) {
//                continue;
//            }
//
//            Collection<Object> value = convertToPropertyPathSpecificType(entry.getValue(), propertyPath);
////            Optional<Predicate> predicate = invokeBinding(propertyPath, bindings, value);
//            invokeBinding(value, propertyMap.get(propertyPath.getLeafProperty()), t);
////            predicate.ifPresent(builder::and);
//        }
//
//        return t;
//    }

    /**
     * Returns whether the given {@link Predicate} represents an empty predicate instance.
     *
     * @param predicate
     * @return
     * @since 2.5.3
     * @see BooleanBuilder
     */
    public static boolean isEmpty(Predicate predicate) {
        return new BooleanBuilder().equals(predicate);
    }


    private <T> void invokeBinding(Collection<Object> values, PropertyDescriptor propertyDescriptor, T t) {
        try {
            propertyDescriptor.getWriteMethod().invoke(t, values.toArray());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Predicate> bind(Path<?> path, Collection<? extends Object> value) {

        Assert.notNull(path, "Path must not be null");
        Assert.notNull(value, "Value must not be null");

        if (value.isEmpty()) {
            return Optional.empty();
        }

        if (path instanceof CollectionPathBase cpb) {

            BooleanBuilder builder = new BooleanBuilder();

            for (Object element : value) {

                if (element instanceof Collection<?> nestedCollection) {

                    for (Object nested : nestedCollection) {
                        builder.and(cpb.contains(nested));
                    }
                } else {
                    builder.and(cpb.contains(element));
                }

            }

            return Optional.of(builder.getValue());
        }

        if (path instanceof SimpleExpression expression) {

            if (value.size() > 1) {
                return Optional.of(expression.in(value));
            }

            Object object = value.iterator().next();

            return Optional.of(object == null //
                    ? expression.isNull() //
                    : expression.eq(object));
        }

        throw new IllegalArgumentException(
                String.format("Cannot create predicate for path '%s' with type '%s'", path, path.getMetadata().getPathType()));
    }

    /**
     * Invokes the binding of the given values, for the given {@link PropertyPath} and {@link QuerydslBindings}.
     *
     * @param dotPath must not be {@literal null}.
     * @param bindings must not be {@literal null}.
     * @param values must not be {@literal null}.
     * @return
     */
    private Optional<Predicate> invokeBinding(PathInformation dotPath, QuerydslBindings bindings,
                                              Collection<Object> values) {

        Path<?> path = getPath(dotPath, bindings);

        return bindings.getBindingForPath(dotPath).orElse(defaultBinding).bind(path, values);
    }

    /**
     * Returns the {@link Path} for the given {@link PropertyPath} and {@link QuerydslBindings}. Will try to obtain the
     * {@link Path} from the bindings first but fall back to reifying it from the PropertyPath in case no specific binding
     * has been configured.
     *
     * @param path must not be {@literal null}.
     * @param bindings must not be {@literal null}.
     * @return
     */
    private Path<?> getPath(PathInformation path, QuerydslBindings bindings) {

        Optional<Path<?>> resolvedPath = bindings.getExistingPath(path);

        return resolvedPath.orElseGet(() -> paths.computeIfAbsent(path, it -> it.reifyPath(resolver)));
    }

    /**
     * Converts the given source values into a collection of elements that are of the given {@link PropertyPath}'s type.
     * Considers a single element list with an empty object an empty collection because this basically indicates the
     * property having been submitted but no value provided.
     *
     * @param source must not be {@literal null}.
     * @param path must not be {@literal null}.
     * @return
     */
    private Collection<Object> convertToPropertyPathSpecificType(List<?> source, PathInformation path) {

        if (source.isEmpty() || isSingleElementCollectionWithEmptyItem(source)) {
            return Collections.emptyList();
        }

        TypeDescriptor targetType = getTargetTypeDescriptor(path);
        Collection<Object> target = new ArrayList<>(source.size());

        for (Object value : source) {
            target.add(getValue(targetType, value));
        }

        return target;
    }

    @Nullable
    private Object getValue(TypeDescriptor targetType, Object value) {

        if (ClassUtils.isAssignableValue(targetType.getType(), value)) {
            return value;
        }

        if (conversionService.canConvert(value.getClass(), targetType.getType())) {
            return conversionService.convert(value, TypeDescriptor.forObject(value), targetType);
        }

        return value;
    }

    /**
     * Returns the target {@link TypeDescriptor} for the given {@link PathInformation} by either inspecting the field or
     * property (the latter preferred) to pick up annotations potentially defined for formatting purposes.
     *
     * @param path must not be {@literal null}.
     * @return
     */
    private static TypeDescriptor getTargetTypeDescriptor(PathInformation path) {

        PropertyDescriptor descriptor = path.getLeafPropertyDescriptor();

        Class<?> owningType = path.getLeafParentType();
        String leafProperty = path.getLeafProperty();

        TypeDescriptor result = descriptor == null //
                ? TypeDescriptor
                .nested(org.springframework.data.util.ReflectionUtils.findRequiredField(owningType, leafProperty), 0)
                : TypeDescriptor
                .nested(new Property(owningType, descriptor.getReadMethod(), descriptor.getWriteMethod(), leafProperty), 0);

        if (result == null) {
            throw new IllegalStateException(String.format("Could not obtain TypeDescriptor for PathInformation %s", path));
        }

        return result;
    }

    /**
     * Returns whether the given collection has exactly one element that is empty (i.e. doesn't contain text). This is
     * basically an indicator that a request parameter has been submitted but no value for it.
     *
     * @param source must not be {@literal null}.
     * @return
     */
    private static boolean isSingleElementCollectionWithEmptyItem(List<?> source) {
        return source.size() == 1 && ObjectUtils.isEmpty(source.get(0));
    }

    /**
     * Returns the {@link Predicate} from {@link BooleanBuilder}.
     *
     * @param builder
     * @return
     */
    private static Predicate getPredicate(BooleanBuilder builder) {

        Predicate predicate = builder.getValue();
        return predicate == null ? new BooleanBuilder() : predicate;
    }
}
