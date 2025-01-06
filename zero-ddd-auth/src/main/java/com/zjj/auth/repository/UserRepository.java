package com.zjj.auth.repository;

import com.zjj.auth.entity.User;
import io.vavr.control.Option;
import org.springframework.data.repository.Repository;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年01月06日 09:07
 */
@org.springframework.stereotype.Repository
public interface UserRepository extends Repository<User,Long> {

    Option<User> findByUsername(String username);
}
