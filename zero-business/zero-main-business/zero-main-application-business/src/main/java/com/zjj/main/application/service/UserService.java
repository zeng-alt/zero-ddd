package com.zjj.main.application.service;

import com.zjj.autoconfigure.component.security.UserProfile;
import com.zjj.main.infrastructure.db.jpa.entity.User;
import io.vavr.control.Option;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月08日 11:34
 */
public interface UserService {

    Option<UserProfile> findProfileByUsername(String username);

    Option<User> findByUsername(String username);

    Option<User> findById(Long id);
}
