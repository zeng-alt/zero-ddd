package com.zjj.auth;

import com.zjj.l2.cache.component.supper.L2CacheManage;
import com.zjj.l2.cache.component.supper.RedissonCaffeineCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月12日 20:27
 */
@SpringBootTest
public class L2Test {

    @Autowired
    private L2CacheManage l2CacheManage;

    private RedissonCaffeineCache l2Cache;

    @BeforeEach
    public void before() {
        l2Cache = (RedissonCaffeineCache) l2CacheManage.getCache("user");
    }

    @Test
    public void testPut() {
        l2Cache.put("zhengSai", "hello");
    }


    @Test
    public void testGet() {
        String s = l2Cache.get("zhengSai", String.class);
        Assertions.assertEquals("hello", s);
    }


    @Test
    public void testPutNull() {
        l2Cache.put("null", null);
    }


    @Test
    public void testGetNull() {

        User s = l2Cache.get("null1", User.class);
        Assertions.assertEquals(null, s);
    }


    @Test
    public void testPutObject() {
        User user = User.createUser();
        l2Cache.put("user", user);
    }


    @Test
    public void testGetObject() {
        User user = User.createUser();
        User s = l2Cache.get("user", User.class);
        Assertions.assertEquals(user, s);
    }
}
