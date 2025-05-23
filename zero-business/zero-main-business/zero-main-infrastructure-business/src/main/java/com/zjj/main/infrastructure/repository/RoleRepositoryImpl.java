package com.zjj.main.infrastructure.repository;

import com.zjj.bean.componenet.BeanHelper;
import com.zjj.main.domain.permission.PermissionAgg;
import com.zjj.main.domain.permission.PermissionId;
import com.zjj.main.domain.role.RoleAgg;
import com.zjj.main.domain.role.RoleId;
import com.zjj.main.domain.role.RoleRepository;
import com.zjj.main.infrastructure.db.jpa.dao.RoleDao;
import com.zjj.main.infrastructure.db.jpa.entity.Role;
import com.zjj.main.infrastructure.db.jpa.entity.RolePermission;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.jmolecules.ddd.types.Association;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        return roleDao.findById(roleId).map(r -> {
            RoleAgg roleAgg = BeanHelper.copyToObject(r, RoleAgg.class);
            roleAgg.setId(RoleId.of(r.getId()));
            List<Association<PermissionAgg, PermissionId>> permissionIds = this.convert(r.getRolePermissions());
//                    = r.getRolePermissions()
//                        .stream()
//                        .map(rp -> rp.getPermission().getId())
//                        .map(PermissionId::of)
//                        .map(l -> (Association<PermissionAgg, PermissionId>) Association.forId(l))
//                        .toList();
            roleAgg.setPermissions(permissionIds);
            return roleAgg;
        });
    }

    @Override
    public Option<RoleAgg> findByRoleKey(String roleKey) {
        return BeanHelper.copyToOptionObject(
                roleDao.findByCode(roleKey),
                RoleAgg.class
        );
    }

    @Override
    public void save(RoleAgg roleAgg) {
        roleDao.save(BeanHelper.copyToObject(roleAgg, Role.class));
    }

    @Override
    public List<com.zjj.main.domain.role.Role> findAllByIdIn(Set<Long> ids) {
        return this.roleDao
                .findAllByIdIn(ids)
                .stream()
                .map(r -> {
                    RoleAgg roleAgg = BeanHelper.copyToObject(r, RoleAgg.class);
                    roleAgg.setId(RoleId.of(r.getId()));
                    List<Association<PermissionAgg, PermissionId>> permissionIds = this.convert(r.getRolePermissions());
                    roleAgg.setPermissions(permissionIds);
                    return (com.zjj.main.domain.role.Role) roleAgg;
                })
                .toList();

    }

    @Override
    public boolean existsByCode(String code) {
        return roleDao.existsByCode(code);
    }

    private List<Association<PermissionAgg, PermissionId>> convert(Set<RolePermission> rolePermissions) {
        return rolePermissions.stream()
                .map(rp -> rp.getPermission().getId())
                .map(PermissionId::of)
                .map(l -> (Association<PermissionAgg, PermissionId>) Association.forId(l))
                .toList();
    }
}
