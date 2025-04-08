package com.zjj.auth.repository;

import com.zjj.auth.entity.UserSession;
import org.springframework.stereotype.Repository;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月08日 11:00
 */
@Repository
public interface UserSessionRepository  extends org.springframework.data.repository.Repository<UserSession, Long> {

    public void save(UserSession userSession);
}
