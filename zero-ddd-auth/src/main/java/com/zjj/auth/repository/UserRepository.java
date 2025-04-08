package com.zjj.auth.repository;

import com.zjj.auth.entity.User;
import io.vavr.control.Option;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zengJiaJun
 * @crateTime 2025年01月05日 14:38
 * @version 1.0
 */
@Repository
public interface UserRepository extends org.springframework.data.repository.Repository<User, Long> {

    Option<User> findByUsername(String username);

    List<User> findAll();
}
