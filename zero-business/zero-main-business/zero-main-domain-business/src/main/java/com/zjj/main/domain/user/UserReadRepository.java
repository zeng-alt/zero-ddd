package com.zjj.main.domain.user;

import com.zjj.autoconfigure.component.security.UserProfile;
import com.zjj.main.domain.user.model.UserRecord;
import io.vavr.control.Option;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月02日 21:41
 */
public interface UserReadRepository {

    Option<UserRecord> findByUsername(String username);

    UserProfile findProfileByUsername(String username);
}
