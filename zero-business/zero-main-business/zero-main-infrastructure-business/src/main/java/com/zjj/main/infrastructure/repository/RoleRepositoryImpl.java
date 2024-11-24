package com.zjj.main.infrastructure.repository;

import com.zjj.bean.componenet.BeanHelper;
import com.zjj.main.domain.role.RoleAgg;
import com.zjj.main.domain.role.RoleRepository;
import com.zjj.main.infrastructure.db.jpa.dao.RoleDao;
import com.zjj.main.infrastructure.db.jpa.entity.Role;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月21日 21:20
 */
@Component
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleDao roleDao;

    @Override
    public Option<RoleAgg> findById(Long roleId) {
        return BeanHelper.copyToOptionObject(
                roleDao.findById(roleId),
                RoleAgg.class
        );
    }

    @Override
    public Option<RoleAgg> findByRoleKey(String roleKey) {
        return BeanHelper.copyToOptionObject(
                roleDao.findByRoleKey(roleKey),
                RoleAgg.class
        );
    }

    @Override
    public void save(RoleAgg roleAgg) {
        roleDao.save(BeanHelper.copyToObject(roleAgg, Role.class));
    }
}
