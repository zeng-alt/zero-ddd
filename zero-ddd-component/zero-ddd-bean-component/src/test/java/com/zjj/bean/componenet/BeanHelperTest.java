package com.zjj.bean.componenet;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StopWatch;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月15日 20:54
 */
public class BeanHelperTest {

    @Test
    public void copyToObject() {
        User user = User.create();
        User1 user1 = BeanHelper.copyToObject(user, User1.class);
        Assertions.assertEquals(user1.getName(), "张三");
        Assertions.assertEquals(user1.getAge(), 18);
    }


    @Test
    public void copyToList() {
        User user = User.create();
        User user2 = User.create();
        List<User> users = List.of(user, user2);

        List<User1> user1s = new ArrayList<>();
        BeanUtils.copyProperties(users, user1s);

        List<User1> user1 = BeanHelper.copyToList(users, User1.class);
        for (User1 u : user1) {
            Assertions.assertEquals(u.getName(), "张三");
            Assertions.assertEquals(u.getAge(), 18);
        }

    }


    @Test
    public void timeTest() throws InvocationTargetException, IllegalAccessException {

        ArrayList<User> users = new ArrayList<>();
        ArrayList<User1> user1s1 = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            users.add(User.create());
            user1s1.add(new User1());
        }

        StopWatch stopWatch2 = new StopWatch();
        stopWatch2.start();
        org.apache.commons.beanutils.BeanUtils.copyProperties(users, user1s1);
        stopWatch2.stop();
        System.out.println(stopWatch2.getTotalTimeMillis());

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        BeanHelper.copyToList(users, User1.class);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());

        StopWatch stopWatch1 = new StopWatch();
        stopWatch1.start();
        BeanUtils.copyProperties(users, user1s1);
        stopWatch1.stop();
        System.out.println(stopWatch1.getTotalTimeMillis());


        StopWatch stopWatch4 = new StopWatch();
        stopWatch4.start();
        for (User user : users) {
            User1 user1 = new User1();
            BeanUtils.copyProperties(user, user1);
        }

        stopWatch4.stop();
        System.out.println(stopWatch4.getTotalTimeMillis());
    }

    @Test
    public void testInt() {
        ArrayList<Integer> integers = new ArrayList<>(1000);
        for (int i = 0; i < 1000; i++) {
            integers.add(i);
        }

        ArrayList<Integer> target = new ArrayList<>();
        for (int i = 1000; i > 0; i--) {
            target.add(i);
        }
        BeanUtils.copyProperties(integers, target);
        System.out.println();
    }


    @Data
    public static class User {

        private String name;
        private Integer age;

        public static User create() {
            User user = new User();
            user.name = "张三";
            user.age = 18;
            return user;
        }
    }


    @Data
    public static class User1 {
        private String name;
        private Integer age;
    }
}
