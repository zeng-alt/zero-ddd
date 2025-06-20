package com.zjj.security.abac.component.object;

import com.zjj.autoconfigure.component.security.abac.AbacContextEntity;
import com.zjj.security.abac.component.annotation.Schema;
import com.zjj.security.abac.component.annotation.SchemaProperty;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月10日 16:30
 */
public class ObjectEntityParser {


    /**
     * 主入口，根据类自动获取注解名或类名
     */
    public static AbacContextEntity.ObjectEntity parse(Class<?> clazz) {
        String name = clazz.getSimpleName();
        if (clazz.isAnnotationPresent(Schema.class)) {
            name = clazz.getAnnotation(Schema.class).name();
        }
        return parse(name, clazz);
    }

    /**
     * 主解析方法：将一个类解析为 ObjectEntity 对象
     */
    public static AbacContextEntity.ObjectEntity parse(String name, Class<?> clazz) {
        AbacContextEntity.ObjectEntity entity = new AbacContextEntity.ObjectEntity();
        entity.setName(name);
        entity.setAttributes(new ArrayList<>());

        if (clazz == null || clazz.equals(Void.class)) {
            entity.setType("null");
            return entity;
        }

        if (clazz.getPackage() != null &&
                clazz.getPackage().getName().startsWith("java.lang")) {
            entity.setType(clazz.getSimpleName());
            return entity;
        }

        if (clazz.isArray()) {
            entity.setType("Array");
            entity.setIsCollection(true);
            entity.setAssociation(true);
            entity.setAttributes(null); // 可以进一步解析 componentType
            return entity;
        }

        if (Map.class.isAssignableFrom(clazz)) {
            entity.setType("Map");
            entity.setIsMap(true);
            return entity;
        }

        entity.setType("Object");
        entity.setAssociation(true);

        // 设置类上的注解描述
        if (clazz.isAnnotationPresent(Schema.class)) {
            Schema schema = clazz.getAnnotation(Schema.class);
            entity.setDescription(schema.name());
        }

        // 获取所有字段
        Field[] fields = FieldUtils.getAllFields(clazz);
        List<AbacContextEntity.ObjectAttribute> attributes = Arrays.stream(fields)
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .filter(f -> !Modifier.isTransient(f.getModifiers()))
                .map(ObjectEntityParser::toObjectAttribute)
                .collect(Collectors.toList());

        entity.setAttributes(attributes);

        return entity;
    }

    /**
     * 解析字段为 ObjectAttribute
     */
    public static AbacContextEntity.ObjectAttribute toObjectAttribute(Field field) {
        AbacContextEntity.ObjectAttribute attr = new AbacContextEntity.ObjectAttribute();
        attr.setName(field.getName());

        Class<?> fieldType = field.getType();
        attr.setType(fieldType.getSimpleName());

        // 获取描述信息
        if (field.isAnnotationPresent(SchemaProperty.class)) {
            SchemaProperty sp = field.getAnnotation(SchemaProperty.class);
            attr.setDescription(sp.value());
        }

        // Map 类型
        boolean isMap = Map.class.isAssignableFrom(fieldType);
        attr.setIsMap(isMap);

        // Collection 类型
        boolean isCollection = Collection.class.isAssignableFrom(fieldType);
        attr.setCollection(isCollection);

        // 数组处理
        if (fieldType.isArray()) {
            attr.setCollection(true);
            Class<?> componentType = fieldType.getComponentType();
            if (isCustom(componentType)) {
                attr.setAssociation(true);
                attr.setAssociationType(parse(componentType));
            }
        }

        // 泛型集合处理，如 List<Foo>
        else if (isCollection) {
            attr.setType("Collection");
            try {
                Type genericType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                if (genericType instanceof Class<?>) {
                    Class<?> genericClass = (Class<?>) genericType;
                    if (isCustom(genericClass)) {
                        attr.setAssociation(true);
                        attr.setAssociationType(parse(genericClass));
                    }
                }
            } catch (Exception ignored) {
                // 忽略泛型获取异常
            }
        }

        // 其他 Map 类型（可扩展 key/value 类型解析）
        else if (isMap) {
            attr.setType("Map");
        }

        // 自定义对象（非 Java 内建）
        else if (isCustom(fieldType)) {
            attr.setAssociation(true);
            attr.setAssociationType(parse(fieldType));
        }

        return attr;
    }

    /**
     * 判断是否是自定义类型
     */
    private static boolean isCustom(Class<?> type) {
        return !(type.isPrimitive()
                || type.isEnum()
                || type.getName().startsWith("java.")
                || type.getPackageName().startsWith("javax."));
    }

}
