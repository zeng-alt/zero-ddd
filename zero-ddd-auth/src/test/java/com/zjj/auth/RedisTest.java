package com.zjj.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.zjj.autoconfigure.component.json.JsonHelper;
import com.zjj.cache.component.repository.RedisStringRepository;
import io.vavr.collection.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.support.NullValue;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月10日 13:48
 */
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisStringRepository redisStringRepository;
    @Autowired
    private JsonHelper jsonHelper;

    @Test
    void testSaveString() {
        redisStringRepository.put("a", "hello");
        Assertions.assertEquals(redisStringRepository.get("a"), "hello");
    }


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSaveData() throws JsonProcessingException {
        LocalDateTime now = LocalDateTime.now();
        redisStringRepository.put("now", now);
        LocalDateTime now1 = redisStringRepository.get("now");
        LocalDate now2 = LocalDate.now();
        redisStringRepository.put("date", now2);
        LocalDate date = redisStringRepository.get("date");
        redisStringRepository.put("time", LocalTime.now());
        LocalTime time = redisStringRepository.get("time");
//        LocalDateTime o = redisStringRepository.get("now", LocalDateTime.class);
//        System.out.println(jsonHelper.parseObject("2024-10-10 15:11:05", LocalDateTime.class));
//        System.out.println(redisStringRepository.get("now", LocalDateTime.class));
        System.out.println(jsonHelper.toJsonString(now));
        String jsonString = jsonHelper.toJsonString(now);
        String s = objectMapper.writeValueAsString(now);
//        Assertions.assertEquals(redisStringRepository.get("now", LocalDateTime.class), jsonHelper.parseObject(jsonString, LocalDateTime.class));
    }


    @Test
    void testSaveData2() {
        User3 user = new User3();
        user.setName("zjj");
        user.setAge(18);
        redisStringRepository.put("user3", user);
        User3 user3 = redisStringRepository.get("user3");
        Assertions.assertEquals(user3, user);
    }

//    @Test
//    void


    @Test
    void testSaveNull() {
        redisStringRepository.put("null", null);
        Object o = redisStringRepository.get("null");
        Assertions.assertEquals(o, null);
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testSaveNull1() {
        redisStringRepository.put("null1", null);
        Object o = redisStringRepository.get("null1");
        System.out.println();
//        Assertions.assertEquals(redisStringRepository.get("null"), null);
    }

    @Test
    void testSaveObject() {
        User user = new User();
        user.setName("zjj");
        user.setAge(18);
        redisStringRepository.put("user", user);
        User user1 = redisStringRepository.get("user");
        Assertions.assertEquals(redisStringRepository.get("user"), user);
    }

    @Test
    void testSaveObject2() {
        User user = new User();
        user.setName("zjj");
        user.setAge(18);
        redisStringRepository.put("user", user);
        Assertions.assertEquals(redisStringRepository.get("user"), user);
    }

    @Test
    void testSaveFinalObject() {
        User1 user = new User1();
        user.setName("zjj");
        user.setAge(18);
        redisStringRepository.put("user1", user);
        Object instance = NullValue.INSTANCE;
        redisStringRepository.put("null", instance);
        NullValue aNull = redisStringRepository.get("null");
//        Assertions.assertThrows(RedisException.class, () -> redisStringRepository.get("user1", User1.class));
    }

    @Autowired
    RedissonClient redissonClient;


    @Test
    void testSaveJavaCollection() {
        java.util.List<String> strings = Lists.newArrayList("a", "b", "c");
        redisStringRepository.put("javaString", strings);
        java.util.List<String> javaString = redisStringRepository.get("javaString");
        Assertions.assertEquals(strings, javaString);
        java.util.List<User3> user3s = Lists.newArrayList(new User3(), new User3(), new User3());
        redisStringRepository.put("javaUser3", user3s);
        java.util.List<User3> javaUser3 = redisStringRepository.get("javaUser3");
        Assertions.assertEquals(user3s, javaUser3);
    }

    @Test
    void testSaveVavrCollection() {
        List<String> strings = List.of("a", "b", "c");
        redisStringRepository.put("vavrString", strings);
        java.util.List<String> vavrString = redisStringRepository.get("vavrString");
        Assertions.assertEquals(strings.toJavaList(), vavrString);
        List<User3> user3s = List.of(new User3(), new User3(), new User3());
        redisStringRepository.put("vavrUser3", user3s);
        java.util.List<User3> vavrUser3 = redisStringRepository.get("vavrUser3");
        Assertions.assertEquals(user3s.toJavaList(), vavrUser3);
    }


    @Data
    @EqualsAndHashCode(exclude = {"now"})
    public static class User3 implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        private String name = "java";
        private Integer age = 34;
        private LocalDateTime now = LocalDateTime.now();
    }

    @Data
    public static class User implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        private String name;
        private Integer age;
    }

    @Data
    public final static class User1 implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        private String name;
        private Integer age;
    }
}
