package com.zjj.example.dao;

import com.zjj.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月20日 17:06
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {
}
