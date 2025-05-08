package com.zjj.cache.component.repository;

import com.google.common.collect.Sets;
import com.zjj.autoconfigure.component.redis.RedisHashRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月14日 17:30
 */
@SpringBootTest
public class TestHashRedisRepository {

    @Autowired
    private RedisHashRepository redisHashRepository;

    @Test
    public void testPutMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("a", "1234");
        hashMap.put("b", "3ettd");
        redisHashRepository.batchPut("test:rbac:master", hashMap);
    }

    @Test
    public void testGet() {
        String a = redisHashRepository.get("test:rbac:master", "a");
        Assertions.assertEquals(a, "1234");
    }

    @Test
    public void testPut() {
        redisHashRepository.put("test:rbac:master", "a", "testput");
        Assertions.assertEquals(redisHashRepository.get("test:rbac:master", "a"), "testput");
    }

    @Test
    void testPutMapSetValue() {
        Map<String, Object> stringSetHashMap = new HashMap<>();
        stringSetHashMap.put("admin", Sets.newHashSet("1234", "get:user"));
        stringSetHashMap.put("user", Sets.newHashSet("1234", "delete:user"));
        redisHashRepository.batchPut("test:role:master", stringSetHashMap);
    }

    @Test
    void testGetSetValue() {
        Set<String> result = redisHashRepository.get("test:role:master", "admin");
        Assertions.assertIterableEquals(result, Sets.newHashSet("1234", "get:user"));
    }


    @Test
    void testPutSetValue() {
        redisHashRepository.put("test:role:master", "admin", Sets.newHashSet("1234", "get:user", "45566"));
        Set<String> result = redisHashRepository.get("test:role:master", "admin");
        assertThat(result).isEqualTo(Sets.newHashSet("45566", "1234", "get:user"));
    }

    @Test
    void testGetResource() {
        Object o = redisHashRepository.get("rbac:master:http", "/main/v1/user/detail:GET");
        System.out.println(o);
    }
}
