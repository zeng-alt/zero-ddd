package com.zjj.auth;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ResolvableType;

import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月11日 08:19
 */
public class BeanTest {

    @Data
    public static class User {
        private String name;
        private Integer age;
    }


    public static class Mapper implements BaseMapper<User> {

    }

    public interface BaseMapper<T> {




        default void test() {
            Class<T> result = null;
            //得到当前对象的父类"泛型类型"(也叫参数化类型)
            //superclass == GenericDao<Dept>成为参数化类型
            //superclass == BaseDao不是参数化类型
            Type superclass = getClass().getGenericSuperclass();
            //判断类型是否为参数化类型
            if (superclass instanceof ParameterizedType) {
                //把父类类型转换成参数化类型（泛型类型）
                //只有ParameterizedType才能通过getActualTypeArguments得到参数
                ParameterizedType parameterizedType = (ParameterizedType) superclass;
                //得到参数化类型类型的参数
                //types == GenericDao<Dept>的"<Dept>"参数
                Type[] types = parameterizedType.getActualTypeArguments();
                //返回子类传递的类型
                result = (Class<T>) types[0];
            }
            Class<T> type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(type);
            System.out.println();
        }
    }

//    @Test
//    void testBean() {
//        Mapper userMapper = new Mapper();
//        userMapper.test();
//    }
}
