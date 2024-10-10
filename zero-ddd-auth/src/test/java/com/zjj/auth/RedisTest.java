package com.zjj.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjj.autoconfigure.component.json.JsonHelper;
import com.zjj.cache.component.repository.RedisStringRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.redisson.client.RedisException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

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
        User3 user3 = redisStringRepository.get("user3", User3.class);
        Assertions.assertEquals(user3, user);
    }

//    @Test
//    void


    @Test
    void testSaveNull() {
        redisStringRepository.put("null", null);
        Assertions.assertEquals(redisStringRepository.get("null"), null);
    }

    @Test
    void testSaveObject() {
        User user = new User();
        user.setName("zjj");
        user.setAge(18);
        redisStringRepository.put("user", user);
        User user1 = redisStringRepository.get("user", User.class);
        Assertions.assertEquals(redisStringRepository.get("user", User.class), user);
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
        redisStringRepository.put("user", user);

        Assertions.assertThrows(RedisException.class, () -> redisStringRepository.get("user1", User1.class));
    }


    @Data
    @EqualsAndHashCode(exclude = {"now"})
    public static class User3 implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        private String name;
        private Integer age;
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
