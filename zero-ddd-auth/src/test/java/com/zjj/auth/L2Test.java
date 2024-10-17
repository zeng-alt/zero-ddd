//package com.zjj.auth;
//
//import com.zjj.autoconfigure.component.l2cache.L2CacheManage;
//import com.zjj.l2.cache.component.supper.RedissonCaffeineCache;
//import org.assertj.core.util.Lists;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * @author zengJiaJun
// * @version 1.0
// * @crateTime 2024年10月12日 20:27
// */
//@SpringBootTest
//public class L2Test {
//
//    @Autowired
//    private L2CacheManage l2CacheManage;
//
//    private RedissonCaffeineCache l2Cache;
//
//    @BeforeEach
//    public void before() {
//        l2Cache = (RedissonCaffeineCache) l2CacheManage.getCache("user");
//    }
//
//    @Test
//    public void testPut() {
//        l2Cache.put("zhengSai", "hello");
//    }
//
//
//    @Test
//    public void testGet() {
//        String s = l2Cache.get("zhengSai", String.class);
//        Assertions.assertEquals("hello", s);
//    }
//
//
//    @Test
//    public void testPutNull() {
//        l2Cache.put("null", null);
//    }
//
//
//    @Test
//    public void testGetNull() {
//
//        User s = l2Cache.get("null1", User.class);
//        Assertions.assertEquals(null, s);
//    }
//
//
//    @Test
//    public void testPutObject() {
//        User user = User.createUser();
//        l2Cache.put("user", user);
//    }
//
//
//    @Test
//    public void testGetObject() {
//        User user = User.createUser();
//        User s = l2Cache.get("user", User.class);
//        Assertions.assertEquals(user, s);
//    }
//
//    @Test void testGetAll() {
//        List<String> users = Lists.newArrayList("user1", "user2", "user3", "user4");
//        Map<Object, Object> allPresent = l2Cache.getAll(users, s -> Map.of("user1", "张三", "user2", "李四", "user3", "王五", "user4", "赵六"));
//        String s = l2Cache.get("user1", String.class);
//        //        Map<Object, Object> allPresent = l2Cache.getAllPresent(users);
//        for (Map.Entry<Object, Object> entry : allPresent.entrySet()) {
//            System.out.println(entry.getKey() + " = " + entry.getValue());
//        }
//    }
//}
