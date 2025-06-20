package com.zjj.graphql.component.utils;


import graphql.language.ListType;
import graphql.language.Type;
import graphql.language.TypeName;
import jakarta.persistence.metamodel.Attribute;
import org.hibernate.metamodel.model.domain.internal.SetAttributeImpl;
import org.hibernate.metamodel.model.domain.internal.SingularAttributeImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

import static io.vavr.API.*;
import static io.vavr.Predicates.isIn;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月17日 21:39
 */
public class TypeMatchUtils {


    private TypeMatchUtils() {}


//    public static Type structInput(Attribute<?, ?> attribute) {
//        String prefix = "";
//        if (attribute.isAssociation()) {
//            prefix = "Input";
//        }
//
//        if (attribute.isCollection()) {
//            // 获取集合元素的 Java 类型
//            Class<?> elementType = ((SetAttributeImpl) attribute).getBindableJavaType();
//
//            // 创建 ListType
//            return ListType.newListType()
//                    .type(TypeName.newTypeName().name(prefix + matchType(elementType)).build())
//                    .build();
//        } else {
//            // 获取属性的 Java 类型
//            Class<?> javaType = attribute.getJavaType();
//            // 创建 TypeName
//            return TypeName.newTypeName()
//                    .name(prefix + matchType(javaType))
//                    .build();
//        }
//    }

//    public static Type structType(Attribute<?, ?> attribute) {
//        if (attribute.isCollection()) {
//            // 获取集合元素的 Java 类型
//            Class<?> elementType = ((SetAttributeImpl) attribute).getBindableJavaType();
//            // 创建 ListType
//            return ListType.newListType()
//                    .type(TypeName.newTypeName().name(matchType(elementType)).build())
//                    .build();
//        } else {
//            // 获取属性的 Java 类型
//            Class<?> javaType = attribute.getJavaType();
//            // 创建 TypeName
//            return TypeName.newTypeName()
//                    .name(matchType(javaType))
//                    .build();
//        }
//    }

//    public static String matchType(Attribute<?, ?> attribute) {
//        if (attribute.isCollection()) {
//            String result = "[%s]";
//            Class bindableJavaType = ((SetAttributeImpl) attribute).getBindableJavaType();
//            result = result.formatted(matchType(bindableJavaType));
//            return result;
//        }
//
//        return matchType(attribute.getJavaType());
//    }


    public static String matchType(Attribute<?, ?> attribute) {
        if (attribute instanceof SingularAttributeImpl.Identifier identifier) {
            if (identifier.isId()) {
                return "ID";
            }
        }
        return matchType(attribute.getJavaType());
    }

    public static String matchType(Class clasz) {
        return Match(clasz).of(
                Case($(String.class), "String"),
                Case($(BigInteger.class), "BigInteger"),
                Case($(Boolean.class), "Boolean"),
                Case($(Integer.class), "Int"),
                Case($(Long.class), "Long"),
                Case($(isIn(BigDecimal.class)), "BigDecimal"),
                Case($(isIn(Float.class, Double.class)), "Float"),
                Case($(Byte.class), "Byte"),
                Case($(LocalDate.class), "Date"),
                Case($(LocalTime.class), "LocalTime"),
                Case($(LocalDateTime.class), "LocalDateTime"),
                Case($(Instant.class), "Long"),
                Case($(isIn(Date.class, Timestamp.class)), () -> {
                    System.out.println();
                    throw new IllegalArgumentException("不支持类型" + clasz.getTypeName() + ", 请 "+ clasz +" 修改成jdk8的时间类型");
                }),
                Case($(), clasz.getSimpleName())
        );
    }
}
