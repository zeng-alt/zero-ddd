package com.zjj.example.service;

import com.zjj.example.dao.UserDao;
import com.zjj.example.entity.User;
import lombok.Data;
import org.jmolecules.event.annotation.DomainEvent;
import org.jmolecules.event.annotation.DomainEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.modulith.events.Externalized;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月21日 16:24
 */
@Component
public class UserHandler {

    @Autowired
    private UserDao userDao;

    @DomainEvent
    @Data
    public static class UserEvent {
        private String name;
        private Integer age;
    }

    @ApplicationModuleListener
    public void on(UserEvent userEvent) {
        System.out.println("UserEventHandler:" + userEvent);

        User user = new User();
        user.setName(userEvent.getName());
        user.setAge(userEvent.getAge());
        userDao.save(user);
    }
}
