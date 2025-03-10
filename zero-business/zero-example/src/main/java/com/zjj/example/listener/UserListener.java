package com.zjj.example.listener;


import com.zjj.example.entity.UserEvent;
import com.zjj.example.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;


/**
 * @author zengJiaJun
 * @crateTime 2025年03月10日 21:17
 * @version 1.0
 */
@Component
@RabbitListener(queues = "zero-example")
public class UserListener {

//    @ApplicationModuleListener
    @RabbitHandler
    public void on(UserService.ExternalizedUserEvent event) {
        System.out.println("接收到事件：" + event);
    }
}
