package com.zjj.main.infrastructure.policy;

import com.zjj.bean.componenet.BeanHelper;
import com.zjj.main.domain.user.event.StockInUserEvent;
import com.zjj.main.domain.user.event.UpdateUserPasswordEvent;
import com.zjj.main.infrastructure.db.jpa.dao.UserDao;
import com.zjj.main.infrastructure.db.jpa.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月24日 17:12
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserPolicy {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @ApplicationModuleListener
    public void on(StockInUserEvent event) {
        userDao.save(BeanHelper.copyToObject(event, User.class));
    }

    @ApplicationModuleListener
    public void on(UpdateUserPasswordEvent event) {
        userDao
                .findById(event.getUserId())
                .peek(u -> u.setPassword(passwordEncoder.encode(event.getPassword())))
                .forEach(userDao::save);
    }
}
