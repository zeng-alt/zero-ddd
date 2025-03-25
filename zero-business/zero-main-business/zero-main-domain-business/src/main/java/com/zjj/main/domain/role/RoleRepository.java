package com.zjj.main.domain.role;

import io.vavr.control.Option;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月24日 16:11
 */
public interface RoleRepository {
    Option<RoleAgg> findById(Long id);
    Option<RoleAgg> findByRoleKey(String roleKey);
    void save(RoleAgg roleAgg);
}
