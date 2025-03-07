package com.zjj.example.controller;

import com.zjj.domain.component.command.CommandBus;
import com.zjj.example.service.UserCommand;
import com.zjj.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月20日 17:09
 */
@RestController
@RequestMapping("/test")
public class UserController {

    @Autowired
    private CommandBus commandBus;

    @GetMapping
    public ResponseEntity<Void> save() {
        UserCommand userCommand = new UserCommand();
        userCommand.setName("张三");
        userCommand.setAge(18);
        commandBus.emit(userCommand);
        return ResponseEntity.ok().build();
    }

    @GetMapping("externalized")
    public ResponseEntity<Void> externalized() {
        UserService.UserExternalizedCommand userCommand = new UserService.UserExternalizedCommand();
        userCommand.setName("张三");
        userCommand.setAge(18);
        commandBus.emit(userCommand);
        return ResponseEntity.ok().build();
    }
}
