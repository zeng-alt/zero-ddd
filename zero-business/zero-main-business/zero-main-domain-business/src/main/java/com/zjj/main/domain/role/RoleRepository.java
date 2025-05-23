package com.zjj.main.domain.role;

import io.vavr.control.Option;

import java.util.List;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月24日 16:11
 */
public interface RoleRepository {
    Option<RoleAgg> findById(Long id);
    Option<RoleAgg> findByRoleKey(String roleKey);
    void save(RoleAgg roleAgg);

    List<Role> findAllByIdIn(Set<Long> ids);

    boolean existsByCode(String code);
}
