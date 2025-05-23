package com.zjj.main.domain.role;

import com.zjj.bean.componenet.ApplicationContextHelper;
import com.zjj.domain.component.Aggregate;
import com.zjj.main.domain.permission.PermissionAgg;
import com.zjj.main.domain.permission.PermissionId;
import com.zjj.main.domain.role.cmd.*;
import com.zjj.main.domain.role.event.*;
import lombok.Data;
import org.jmolecules.ddd.types.Association;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月24日 16:04
 */
@Data
public class RoleAgg extends Aggregate<RoleAgg, RoleId> implements Role {

    private RoleId id;
    private String name;
    private String code;
    private String roleSort;
    private Boolean enable;
    private Integer deleted;
    private List<Association<PermissionAgg, PermissionId>> permissions;

    public void stockIn(StockInRoleCmd cmd) {
        Set<Long> originalSet = new HashSet<>();

        if (!CollectionUtils.isEmpty(this.permissions)) {
            this.permissions
                    .stream()
                    .map(permission -> permission.getId().getId())
                    .forEach(originalSet::add);
        }

        if (this.isNew()) {
            StockInRoleEvent event = new StockInRoleEvent();
            BeanUtils.copyProperties(this, event);
            ApplicationContextHelper.publisher().publishEvent(event);
        } else {
            if (!this.code.equals(cmd.code())) {
                throw new RuntimeException("角色编码不允许修改");
            }
        }

        Set<Long> modifiedSet = CollectionUtils.isEmpty(cmd.permissionIds()) ? new HashSet<>() : new HashSet<>(cmd.permissionIds());

        // 新增的 ID（modified 中有，original 中没有）
        Set<Long> addedIds = new HashSet<>(modifiedSet);
        addedIds.removeAll(originalSet);

        if (!CollectionUtils.isEmpty(addedIds)) {
            StockInRolePermissionEvent event = new StockInRolePermissionEvent();
            event.setRoleCode(this.getCode());
            event.setPermissionId(addedIds);
            ApplicationContextHelper.publisher().publishEvent(event);
        }

        // 删除的 ID（original 中有，modified 中没有）
        Set<Long> removedIds = new HashSet<>(originalSet);
        removedIds.removeAll(modifiedSet);

        if (!CollectionUtils.isEmpty(removedIds)) {
            DeleteRolePermissionEvent event = new DeleteRolePermissionEvent();
            event.setRoleCode(this.getCode());
            event.setPermissionId(removedIds);
            ApplicationContextHelper.publisher().publishEvent(event);
        }

    }

    @Override
    public void functionAuthorize(FunctionAuthorizeCmd cmd) {
        Set<Long> permissionId = CollectionUtils.isEmpty(this.permissions)
                                    ? new HashSet<>()
                                    : this.permissions.stream().map(permission -> permission.getId().getId()).collect(Collectors.toSet());

        Set<Long> graphqlIds = cmd.getGraphqlIds();
        graphqlIds.removeAll(permissionId);
        ApplicationContextHelper.publisher().publishEvent(FunctionAuthorizeEvent.of(graphqlIds, this.id.getId()));
    }

    @Override
    public void functionCancelAuthorize(FunctionCancelAuthorizeCmd cmd) {
        if (CollectionUtils.isEmpty(this.permissions)) {
            return;
        }
        Set<Long> permissionId = this.permissions.stream().map(permission -> permission.getId().getId()).collect(Collectors.toSet());
        Set<Long> graphqlIds = cmd.getGraphqlIds();
        // 取交集
        graphqlIds.retainAll(permissionId);
        ApplicationContextHelper.publisher().publishEvent(FunctionCancelAuthorizeEvent.of(graphqlIds, this.id.getId()));
    }

    @Override
    public void serviceAuthorize(ServiceAuthorizeCmd cmd) {
        ApplicationContextHelper.publisher().publishEvent(ServiceAuthorizeEvent.of(cmd.getService(), this.id.getId()));
    }

    @Override
    public void serviceCancelAuthorize(ServiceCancelAuthorizeCmd cmd) {
        if (CollectionUtils.isEmpty(this.permissions)) {
            return;
        }
        ApplicationContextHelper.publisher().publishEvent(ServiceCancelAuthorizeEvent.of(cmd.getService(), this.id.getId()));
    }
}
