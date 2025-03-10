package com.zjj.example.service;

import com.zjj.example.dao.UserDao;
import com.zjj.example.entity.User;
import lombok.Data;
import org.jmolecules.architecture.cqrs.Command;
import org.jmolecules.architecture.cqrs.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.modulith.events.Externalized;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月20日 17:07
 */
@Service
public class UserService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @CommandHandler
    public void handler(UserCommand command) {
        UserHandler.UserEvent userEvent = new UserHandler.UserEvent();
        userEvent.setName(command.getName());
        userEvent.setAge(command.getAge());
        applicationEventPublisher.publishEvent(userEvent);
    }

    @CommandHandler
    public void handler(UserExternalizedCommand command) {
        ExternalizedUserEvent userEvent = new ExternalizedUserEvent();
        userEvent.setName(command.getName());
        userEvent.setAge(command.getAge());
        applicationEventPublisher.publishEvent(userEvent);
    }

    @Data
    @Command
    public static class UserExternalizedCommand {
        private String name;
        private Integer age;
    }


    @Data
    @Externalized("ddd-event-test::zero-example")
    public static class ExternalizedUserEvent {
        private String name;
        private Integer age;
    }
}
