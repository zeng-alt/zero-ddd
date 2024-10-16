package com.zjj.auth;

import com.zjj.cache.component.repository.RedisSubPubRepository;
import org.junit.jupiter.api.Test;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zengJiaJun
 * @crateTime 2024年10月14日 21:22
 * @version 1.0
 */
@SpringBootTest
public class RedisTopicTest {



    @Autowired
    private RedisSubPubRepository repository;


    @Test
    public void testPus() {
//        repository.publish("topic1", "hello");
        System.out.println(repository.publish("topic1", User.createUser()));
    }

    @Test
    public void testSub() {
        String topic1 = repository.addListener("topic1", User.class, new MessageListener<User>() {
            @Override
            public void onMessage(CharSequence channel, User msg) {
                System.out.println(channel + " " + msg.toString());
            }
        });
        repository.publish("topic1", User.createUser());
        //        System.out.println(topic1);
        //        repository.removeAllListeners("topic1");
//        repository.removeListener("topic1", "b43c9157a6b171a5bd05598b77b064c9");
    }

    @Test
    public void testRemoveListener() {
//        String topic1 = repository.addListener("topic1", User.class, s -> System.out.println(s));
//        repository.removeAllListeners("topic1");
        repository.removeListener("topic1", "1111");
    }
}
