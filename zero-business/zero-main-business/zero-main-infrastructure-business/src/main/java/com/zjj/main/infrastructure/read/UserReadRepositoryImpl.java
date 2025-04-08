package com.zjj.main.infrastructure.read;

import com.zjj.autoconfigure.component.security.UserProfile;
import com.zjj.bean.componenet.BeanHelper;
import com.zjj.main.domain.user.UserReadRepository;
import com.zjj.main.domain.user.model.RoleRecord;
import com.zjj.main.domain.user.model.UserRecord;
import com.zjj.main.infrastructure.db.jpa.dao.UserDao;
import com.zjj.main.infrastructure.db.jpa.entity.UserRole;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月02日 15:42
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserReadRepositoryImpl implements UserReadRepository {

    private final UserDao userDao;

    @Override
    public Option<UserRecord> findByUsername(String username) {
        return userDao.findByUsername(username).map(u -> {
            UserRecord userRecord = new UserRecord();
            UserProfile userProfile = new UserProfile();
            BeanUtils.copyProperties(u, userProfile);
            BeanUtils.copyProperties(u, userRecord);
            Set<RoleRecord> roleRecords = u.getUserRoles().stream().map(UserRole::getRole).map(r -> {
                RoleRecord roleRecord = new RoleRecord();
                roleRecord.setCode(r.getCode());
                roleRecord.setName(r.getName());
                roleRecord.setEnable(r.getEnable());
                return roleRecord;
            }).collect(Collectors.toSet());
            userRecord.setProfile(userProfile);
            userRecord.setRoles(roleRecords);
            return userRecord;
        });
    }

    @Override
    public UserProfile findProfileByUsername(String username) {
        return userDao
                .findByUsername(username)
                .map(u -> BeanHelper.copyToObject(u, UserProfile.class))
                .getOrElse((UserProfile) null);
    }
}
