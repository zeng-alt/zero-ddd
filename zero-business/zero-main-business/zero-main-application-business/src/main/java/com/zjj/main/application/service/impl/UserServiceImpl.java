package com.zjj.main.application.service.impl;

import com.zjj.autoconfigure.component.security.UserProfile;
import com.zjj.bean.componenet.BeanHelper;
import com.zjj.main.application.service.UserService;
import com.zjj.main.infrastructure.db.jpa.dao.UserDao;
import com.zjj.main.infrastructure.db.jpa.entity.User;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月08日 11:34
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public Option<UserProfile> findProfileByUsername(String username) {
        return userDao.findByUsername(username).map(u -> BeanHelper.copyToObject(u, UserProfile.class));
    }

    @Override
    public Option<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public Option<User> findById(Long id) {
        return userDao.findById(id);
    }
}
