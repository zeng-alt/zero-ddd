package com.zjj.main.infrastructure.db.jpa.mutation;

import com.zjj.graphql.component.spi.EntitySaveHandler;
import com.zjj.main.infrastructure.db.jpa.dao.UserDao;
import com.zjj.main.infrastructure.db.jpa.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月27日 09:56
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserEntitySaveHandler implements EntitySaveHandler<User> {

    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;

    @Override
    public void handler(User entity) {
        if (entity.isNew()) {
            if (userDao.existsByUsername(entity.getUsername())) {
                throw new IllegalArgumentException(entity.getUsername() + " 用户名已存在");
            }
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        }
    }
}
