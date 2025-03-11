package com.zjj.cache.component.repository;

import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.cache.component.domain.TestUserDomain;
import com.zjj.cache.component.domain.TestUserDomain1;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zengJiaJun
 * @crateTime 2025年03月10日 21:58
 * @version 1.0
 */
@SpringBootTest
public class TestRedisStringRepository {

    @Autowired
    private RedisStringRepository redisStringRepository;
    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void testSaveUser() {
        TestUserDomain testUserDomain = new TestUserDomain();
        testUserDomain.setName("zero");
        testUserDomain.setAge(18);
        redisStringRepository.put("testUser", testUserDomain);
    }

    @Test
    public void testGetUser() {
//        TestUserDomain1 testUserDomain = redisStringRepository.get("testUser");
//        System.out.println(testUserDomain);

        RBucket<TestUserDomain1> bucket = redissonClient.getBucket("testUser");
        TestUserDomain1 testUserDomain1 = bucket.get();
    }
}
