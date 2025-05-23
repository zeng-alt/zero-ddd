package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.domain.component.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.User;
import io.vavr.control.Option;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.List;
import java.util.Set;

@GraphQlRepository
public interface UserDao extends BaseRepository<User, Long> {


    List<User> queryAllByIdIn(Iterable<Long> ids);

    Option<User> findById(Long id);

    Option<User> findByUsername(String username);

    boolean existsByUsername(String username);
}