package com.zjj.auth;

import com.zjj.cache.component.repository.RedisStreamRepository;
import org.junit.jupiter.api.Test;
import org.redisson.api.StreamMessageId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月14日 10:39
 */
@SpringBootTest
public class RedisStreamRepositoryTest {

    @Autowired
    RedisStreamRepository redisStreamRepository;


    @Test
    public void testAddEvent() {
        StreamMessageId streamMessageId1 = redisStreamRepository.addEvent("testStream", "hello");
//        redisStreamRepository.readGroupEvent("testStream", "test", streamMessageId1, new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                System.out.println(s);
//            }
//        });

        StreamMessageId streamMessageId2 = redisStreamRepository.addEvent("testStream", User.createUser());
        redisStreamRepository.readEvent("testStream", streamMessageId2, new Consumer<User>() {
            @Override
            public void accept(User s) {
                System.out.println(s);
            }
        });
    }


    @Test
    public void testGroup() {
//        redisStreamRepository.createGroup("testGroupStream", "group1");
//        redisStreamRepository.createConsumer("testGroupStream", "group1", "consumer1");
        StreamMessageId streamMessageId = redisStreamRepository.addEvent("testGroupStream", "hello");
        redisStreamRepository.readGroupEvent("testGroupStream", "group1", streamMessageId, "consumer1", new Consumer<String>() {

            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
    }
}
