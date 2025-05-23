package com.zjj.main.infrastructure.listener;

import com.zjj.main.domain.user.event.DeleteUserRoleEvent;
import com.zjj.main.domain.user.event.StockInUserRoleEvent;
import com.zjj.main.infrastructure.db.jpa.dao.RoleDao;
import com.zjj.main.infrastructure.db.jpa.dao.UserDao;
import com.zjj.main.infrastructure.db.jpa.dao.UserRoleDao;
import com.zjj.main.infrastructure.db.jpa.entity.Role;
import com.zjj.main.infrastructure.db.jpa.entity.User;
import com.zjj.main.infrastructure.db.jpa.entity.UserRole;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Delete;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年05月21日 20:51
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserListener {

    private final UserDao userDao;
    private final UserRoleDao userRoleDao;
    private final RoleDao roleDao;

    @ApplicationModuleListener
    public void on(StockInUserRoleEvent event) {
        log.info("用户角色入库事件：{}", event);
        User user = userDao.findById(event.getUserId()).getOrElseThrow(() -> new RuntimeException("用户不存在"));
        Role role = roleDao.findById(event.getRoleId()).getOrElseThrow(() -> new RuntimeException("角色不存在"));
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        userRoleDao.save(userRole);
    }

    @ApplicationModuleListener
    public void on(DeleteUserRoleEvent event) {
        log.info("用户角色删除事件：{}", event);
        UserRole userRole = userRoleDao.findByUser_IdAndRole_Id(event.getUserId(), event.getRoleId()).getOrElseThrow(() -> new RuntimeException("用户角色不存在"));
        userRoleDao.delete(userRole);
    }
}
