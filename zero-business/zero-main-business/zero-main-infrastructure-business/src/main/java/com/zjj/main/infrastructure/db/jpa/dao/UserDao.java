package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.graphql.component.supper.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.User;
import io.vavr.control.Option;

public interface UserDao extends BaseRepository<User, Long> {

    Option<User> findById(Long id);

    Option<User> findByUsername(String username);

    void save(User user);
}