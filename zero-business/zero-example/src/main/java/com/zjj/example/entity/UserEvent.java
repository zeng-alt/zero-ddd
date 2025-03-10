package com.zjj.example.entity;

import lombok.Data;
import org.springframework.modulith.events.Externalized;

@Data
@Externalized("zero-example")
public class UserEvent {

    private String username;

    private Integer age;

}