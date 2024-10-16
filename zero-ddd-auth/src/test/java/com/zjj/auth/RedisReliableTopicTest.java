package com.zjj.auth;

import com.zjj.cache.component.repository.impl.RedisReliableTopicRepositoryImpl;
import com.zjj.autoconfigure.component.l2cache.CacheOperation;
import com.zjj.autoconfigure.component.l2cache.EvictEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月15日 15:46
 */
@SpringBootTest
public class RedisReliableTopicTest {

    @Autowired
    private RedisReliableTopicRepositoryImpl redisReliableTopicRepository;

    @Test
    public void testPush() {
        redisReliableTopicRepository.addListener("reliableTopic", EvictEvent.class, new Consumer<EvictEvent>() {
            @Override
            public void accept(EvictEvent s) {
                System.out.println(s);
            }
        });
        redisReliableTopicRepository.publish("reliableTopic", new EvictEvent("id", "user", CacheOperation.EVICT, "user"));
    }

    @Test
    public void testSub() {
        redisReliableTopicRepository.addListener("reliableTopic", String.class, new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
        redisReliableTopicRepository.publish("reliableTopic", "hello");
    }

}
