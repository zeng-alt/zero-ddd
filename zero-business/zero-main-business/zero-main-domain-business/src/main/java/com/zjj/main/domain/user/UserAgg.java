package com.zjj.main.domain.user;

import com.zjj.bean.componenet.ApplicationContextHelper;
import com.zjj.domain.component.Aggregate;
import com.zjj.main.domain.role.RoleAgg;
import com.zjj.main.domain.role.RoleId;
import com.zjj.main.domain.role.event.StockInRolePermissionEvent;
import com.zjj.main.domain.user.cmd.AssignRoleCmd;
import com.zjj.main.domain.user.cmd.AssignUserRoleCmd;
import com.zjj.main.domain.user.cmd.CancelAssignRoleCmd;
import com.zjj.main.domain.user.event.DeleteUserRoleEvent;
import com.zjj.main.domain.user.event.StockInUserRoleEvent;
import com.zjj.main.domain.user.event.UpdateUserPasswordEvent;
import io.micrometer.common.util.StringUtils;
import lombok.Data;
import org.jmolecules.ddd.types.Association;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月24日 16:24
 */
@Data
public class UserAgg extends Aggregate<UserAgg, UserId> {

    private UserId id;
    private String username;
    private String password;
    private String nickName;
    private String email;
    private String phoneNumber;
    private String gender;
    private String avatar;
    private String status;
    private Integer deleted;
    private String tenantBy;
    private Set<Association<RoleAgg, RoleId>> roleIds;

    public void updatePassword(String password) {
        // 对密码进行校验
        if (StringUtils.isEmpty(password)) {

        }
        if (password.length() < 6) {

        }
        // 密码必须包含数据和字母
        if (!password.matches(".*[a-zA-Z]+.*") || !password.matches(".*[0-9]+.*")) {

        }

        ApplicationContextHelper.publisher().publishEvent(UpdateUserPasswordEvent.of(this.getId().getId(), password));
    }

    public void cancelAssignRoles(CancelAssignRoleCmd cmd) {
        ApplicationContextHelper.publisher().publishEvent(DeleteUserRoleEvent.of(this.getId().getId(), cmd.roleId()));
    }

    public void assignRoles(AssignRoleCmd cmd) {
        ApplicationContextHelper.publisher().publishEvent(StockInUserRoleEvent.of(this.getId().getId(), cmd.roleId()));
    }

    public void assignUserRoles(AssignUserRoleCmd cmd) {
        Set<Long> originalSet = CollectionUtils.isEmpty(this.roleIds) ? new HashSet<>() : this.roleIds.stream().map(r -> r.getId().getId()).collect(Collectors.toSet());

        Set<Long> modifiedSet = CollectionUtils.isEmpty(cmd.roleIds()) ? new HashSet<>() : new HashSet<>(cmd.roleIds());

        Set<Long> addedIds = new HashSet<>(modifiedSet);
        addedIds.removeAll(originalSet);
        if (!CollectionUtils.isEmpty(addedIds)) {
            for (Long roleId : addedIds) {
                StockInUserRoleEvent event = new StockInUserRoleEvent();
                event.setUserId(this.getId().getId());
                event.setRoleId(roleId);
                ApplicationContextHelper.publisher().publishEvent(event);
            }

        }

        Set<Long> removedIds = new HashSet<>(originalSet);
        removedIds.removeAll(modifiedSet);
        if (!CollectionUtils.isEmpty(removedIds)) {
            for (Long roleId : removedIds) {
                DeleteUserRoleEvent event = new DeleteUserRoleEvent();
                event.setRoleId(roleId);
                event.setUserId(this.getId().getId());
                ApplicationContextHelper.publisher().publishEvent(event);
            }
        }
    }
}
