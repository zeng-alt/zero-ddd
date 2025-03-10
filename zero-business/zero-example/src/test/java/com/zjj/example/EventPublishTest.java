package com.zjj.example;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.modulith.events.Externalized;

/**
 * @author zengJiaJun
 * @crateTime 2025年03月10日 20:55
 * @version 1.0
 */
@SpringBootTest
public class EventPublishTest {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

//    @Test
//    public void testPublish() {
//        UserEvent userEvent = new UserEvent();
//        userEvent.setUsername("张三");
//        userEvent.setAge(15);
//        applicationEventPublisher.publishEvent(userEvent);
//    }



}
