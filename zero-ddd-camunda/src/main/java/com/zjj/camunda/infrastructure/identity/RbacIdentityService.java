package com.zjj.camunda.infrastructure.identity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.identity.*;
import org.camunda.bpm.engine.impl.identity.IdentityOperationResult;
import org.camunda.bpm.engine.impl.identity.IdentityProviderException;
import org.camunda.bpm.engine.impl.identity.ReadOnlyIdentityProvider;
import org.camunda.bpm.engine.impl.identity.WritableIdentityProvider;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.GroupEntity;
import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RbacIdentityService implements ReadOnlyIdentityProvider {

    private final UserService userService;
    private final GroupService roleService;
    private final UserRoleService userRoleService;
    
    // ==================== 用户查询相关方法 ====================
    
    @Override
    public UserQuery createUserQuery() {
        return new CustomUserQuery(this.userService);
    }
    
    @Override
    public UserQuery createUserQuery(CommandContext commandContext) {
        return createUserQuery();
    }

    /**
     * Creates a {@link NativeUserQuery} that allows to select users with native queries.
     *
     * @return NativeUserQuery
     */
    @Override
    public NativeUserQuery createNativeUserQuery() {
        return null;
    }

    // ==================== 用户组查询相关方法 ====================
    
    @Override
    public GroupQuery createGroupQuery() {
        return new CustomGroupQuery(this.roleService);
    }
    
    @Override
    public GroupQuery createGroupQuery(CommandContext commandContext) {
        return createGroupQuery();
    }

    /**
     * @param tenantId
     * @return a {@link Tenant} object for the given id or null if no such tenant
     * exists.
     * @throws IdentityProviderException in case an error occurs
     */
    @Override
    public Tenant findTenantById(String tenantId) {
        return null;
    }

    // ==================== 租户查询相关方法 ====================
    
    @Override
    public TenantQuery createTenantQuery() {
        return new CustomTenantQuery();
    }
    
    @Override
    public TenantQuery createTenantQuery(CommandContext commandContext) {
        return createTenantQuery();
    }
    
    // ==================== 核心认证方法 ====================
    
    /**
     * 密码验证
     */
    @Override
    public boolean checkPassword(String userId, String password) {
        try {
            log.debug("验证用户密码: userId={}", userId);
            return userService.validatePassword(userId, password);
        } catch (Exception e) {
            log.error("密码验证失败: userId={}", userId, e);
            return false;
        }
    }
    
    // ==================== 用户信息查询方法 ====================
    
    /**
     * 根据用户ID查找用户
     */
    @Override
    public UserEntity findUserById(String userId) {
        try {
            log.debug("查找用户: userId={}", userId);
            MainUser user = userService.findById(userId);
            return convertToUserEntity(user);
        } catch (Exception e) {
            log.error("查找用户失败: userId={}", userId, e);
            return null;
        }
    }
    
    // ==================== 用户组信息查询方法 ====================
    
    /**
     * 根据组ID查找用户组
     */
    @Override
    public GroupEntity findGroupById(String groupId) {
        try {
            log.debug("查找用户组: groupId={}", groupId);
            MainGroup group = roleService.findById(groupId);
            return convertToGroupEntity(group);
        } catch (Exception e) {
            log.error("查找用户组失败: groupId={}", groupId, e);
            return null;
        }
    }
    

    
    // ==================== 实体转换方法 ====================
    
    /**
     * CustomUser 转换为 UserEntity
     */
    private UserEntity convertToUserEntity(MainUser user) {
        if (user == null) return null;
        
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        return userEntity;
    }

    /**
     * CustomGroup 转换为 GroupEntity
     */
    private GroupEntity convertToGroupEntity(MainGroup group) {
        if (group == null) return null;
        
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setId(group.getId());
        groupEntity.setName(group.getName());
        groupEntity.setType(group.getType());
        return groupEntity;
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() {

    }

    // ==================== 查询参数类 ====================
    
    /**
     * 用户查询参数
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserQueryParams {
        private String userId;
        private String userEmail;
        private String userFirstName;
        private String userLastName;
        private String[] userIdIn;
        private String groupId;
        private Integer offset;
        private Integer maxResults;
    }
    
    /**
     * 用户组查询参数
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupQueryParams {
        private String groupId;
        private String groupName;
        private String groupType;
        private String[] groupIdIn;
        private String userId;
        private Integer offset;
        private Integer maxResults;
    }
}