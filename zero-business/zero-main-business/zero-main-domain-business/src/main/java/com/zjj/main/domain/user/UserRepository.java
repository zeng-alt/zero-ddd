package com.zjj.main.domain.user;

import io.vavr.control.Option;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月24日 16:33
 */
public interface UserRepository {

    Option<UserAgg> findByUsername(String username);

    Option<UserAgg> findById(Long id);

    void save(UserAgg userAgg);

    Boolean existsByRoles(List<String> roleIds);
}
