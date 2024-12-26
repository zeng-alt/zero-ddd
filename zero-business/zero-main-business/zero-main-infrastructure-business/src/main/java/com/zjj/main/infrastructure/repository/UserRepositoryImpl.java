package com.zjj.main.infrastructure.repository;

import com.zjj.bean.componenet.BeanHelper;
import com.zjj.main.domain.user.UserAgg;
import com.zjj.main.domain.user.UserRepository;
import com.zjj.main.infrastructure.db.jpa.dao.UserDao;
import com.zjj.main.infrastructure.db.jpa.entity.User;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月18日 21:30
 */
@Service
@RequiredArgsConstructor(onConstructor=@__({@Autowired}))
public class UserRepositoryImpl implements UserRepository {

    private final UserDao userDao;

    @Override
    public Option<UserAgg> findById(long userId) {
        return BeanHelper.copyToOptionObject(
                userDao.findById(userId),
                UserAgg.class,
                (BiConsumer<User, UserAgg>) (user, userAgg) -> userAgg.setRoleIds(user.getUserRoles().stream().map(userRole -> userRole.getRole().getRoleKey()).collect(Collectors.toSet()))
        );
    }

    @Override
    public Option<UserAgg> findByUserName(String username) {
        return BeanHelper.copyToOptionObject(
                userDao.findByUsername(username),
                UserAgg.class,
                (BiConsumer<User, UserAgg>) (user, userAgg) -> userAgg.setRoleIds(user.getUserRoles().stream().map(userRole -> userRole.getRole().getRoleKey()).collect(Collectors.toSet()))
        );
    }

    @Override
    public void save(UserAgg userAgg) {
        userDao.save(BeanHelper.copyToObject(userAgg, User.class));
    }

    @Override
    public boolean existsByRoles(List<String> roleIds) {
        return false;
    }

}
