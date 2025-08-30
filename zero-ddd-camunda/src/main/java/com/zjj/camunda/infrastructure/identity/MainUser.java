package com.zjj.camunda.infrastructure.identity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Repository;

@Entity
@Table(name = "main_user")
public class MainUser implements User {
    
    @Id
    @Column(name = "id")
    private Long userId;
    
    @Column(name = "username")
    private String id;

    @Column(name = "nick_name")
    private String firstName;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "enable")
    private boolean enabled;

    // 实现 User 接口的方法
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return firstName; // 根据需要实现
    }

    @Override
    public void setLastName(String lastName) {
        // 根据需要实现
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    // getter/setter...
}