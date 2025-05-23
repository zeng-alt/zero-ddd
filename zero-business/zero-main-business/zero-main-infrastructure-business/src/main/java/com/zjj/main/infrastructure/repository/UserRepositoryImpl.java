package com.zjj.main.infrastructure.repository;

import com.zjj.bean.componenet.BeanHelper;
import com.zjj.main.domain.role.RoleAgg;
import com.zjj.main.domain.role.RoleId;
import com.zjj.main.domain.user.UserAgg;
import com.zjj.main.domain.user.UserId;
import com.zjj.main.domain.user.UserRepository;
import com.zjj.main.infrastructure.db.jpa.dao.RoleDao;
import com.zjj.main.infrastructure.db.jpa.dao.UserDao;
import com.zjj.main.infrastructure.db.jpa.entity.User;
import com.zjj.main.infrastructure.db.jpa.entity.UserRole;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.jmolecules.ddd.types.Association;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
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
    private final RoleDao roleDao;



    @Override
    public Option<UserAgg> findById(Long userId) {
        return userDao.findById(userId)
                .map(u -> {
                    UserAgg userAgg = new UserAgg();
                    BeanUtils.copyProperties(u, userAgg);
                    userAgg.setId(UserId.of(u.getId()));
                    userAgg.setRoleIds(this.toAssociationRoles(u.getUserRoles()));
                    return userAgg;
                });
    }

    @Override
    public List<UserAgg> queryByIds(Set<Long> ids) {
        return userDao.queryAllByIdIn(ids)
                        .stream()
                        .map(u -> {
                            UserAgg userAgg = new UserAgg();
                            BeanUtils.copyProperties(u, userAgg);
                            userAgg.setId(UserId.of(u.getId()));
                            userAgg.setRoleIds(this.toAssociationRoles(u.getUserRoles()));
                            return userAgg;
                        })
                        .toList();
    }

    @Override
    public Option<UserAgg> findByUsername(String username) {
        return userDao.findByUsername(username)
                .map(u -> {
                    UserAgg userAgg = new UserAgg();
                    BeanUtils.copyProperties(u, userAgg);
                    userAgg.setId(UserId.of(u.getId()));
                    userAgg.setRoleIds(this.toAssociationRoles(u.getUserRoles()));
                    return userAgg;
                });
    }

    @Override
    public void save(UserAgg userAgg) {
        userDao.save(BeanHelper.copyToObject(userAgg, User.class));
    }



    @Override
    public boolean existsByRoleId(Long roleId) {
        return this.roleDao.existsById(roleId);
    }

    private Set<Association<RoleAgg, RoleId>> toAssociationRoles(Set<UserRole> userRole) {
        return userRole.stream().map(this::toAssociationRole).collect(Collectors.toSet());
    }

    private Association<RoleAgg, RoleId> toAssociationRole(UserRole userRole) {
        return Association.forId(RoleId.of(userRole.getRole().getId()));
    }

}
