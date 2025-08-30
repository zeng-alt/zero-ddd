package com.zjj.camunda.infrastructure.identity;

import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.identity.UserQuery;
import org.camunda.bpm.engine.impl.AbstractQuery;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;

import java.util.List;
import java.util.stream.Collectors;

// 4. 自定义用户查询实现
public class CustomUserQuery extends AbstractQuery<UserQuery, User> implements UserQuery {
    
    private final UserService userService;
    private String userId;
    private String userEmail;
    private String userFirstName;
    private String groupId;
    
    public CustomUserQuery(UserService userService) {
        this.userService = userService;
    }
    
    @Override
    public UserQuery userId(String userId) {
        this.userId = userId;
        return this;
    }
    
    @Override
    public UserQuery userEmail(String email) {
        this.userEmail = email;
        return this;
    }

    /**
     * Only select {@link User}s where the email matches the given parameter.
     * The syntax is that of SQL, eg. %activivi%.
     *
     * @param emailLike
     */
    @Override
    public UserQuery userEmailLike(String emailLike) {
        return null;
    }

    @Override
    public UserQuery userFirstName(String firstName) {
        this.userFirstName = firstName;
        return this;
    }
    
    @Override
    public UserQuery memberOfGroup(String groupId) {
        this.groupId = groupId;
        return this;
    }

    /**
     * Only select {@link User}S that are potential starter for the given process definition.
     *
     * @param procDefId
     */
    @Override
    public UserQuery potentialStarter(String procDefId) {
        return null;
    }

    /**
     * Only select {@link User}s that belongs to the given tenant.
     *
     * @param tenantId
     */
    @Override
    public UserQuery memberOfTenant(String tenantId) {
        return null;
    }

    /**
     * Order by user id (needs to be followed by {@link #asc()} or {@link #desc()}).
     */
    @Override
    public UserQuery orderByUserId() {
        return null;
    }

    /**
     * Order by user first name (needs to be followed by {@link #asc()} or {@link #desc()}).
     */
    @Override
    public UserQuery orderByUserFirstName() {
        return null;
    }

    /**
     * Order by user last name (needs to be followed by {@link #asc()} or {@link #desc()}).
     */
    @Override
    public UserQuery orderByUserLastName() {
        return null;
    }

    /**
     * Order by user email  (needs to be followed by {@link #asc()} or {@link #desc()}).
     */
    @Override
    public UserQuery orderByUserEmail() {
        return null;
    }

    @Override
    public long executeCount(CommandContext commandContext) {
        return userService.countUsers(userId, userEmail, userFirstName, groupId);
    }
    
    @Override
    public List<User> executeList(CommandContext commandContext, Page page) {
        List<MainUser> users = userService.findUsers(userId, userEmail, userFirstName, groupId, page);
        return users.stream().map(u -> (User) u).collect(Collectors.toList());
    }
    
    // 其他必需的方法实现...
    @Override
    public UserQuery userIdIn(String... userIds) {
        // 实现根据需要
        return this;
    }
    
    @Override
    public UserQuery userLastName(String lastName) {
        // 实现根据需要
        return this;
    }

    /**
     * Only select {@link User}s where the last name matches the given parameter.
     * The syntax is that of SQL, eg. %activivi%.
     *
     * @param lastNameLike
     */
    @Override
    public UserQuery userLastNameLike(String lastNameLike) {
        return null;
    }

    @Override
    public UserQuery userFirstNameLike(String firstNameLike) {
        // 实现根据需要
        return this;
    }
    
    // ... 其他方法
}