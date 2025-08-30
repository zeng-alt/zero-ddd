package com.zjj.camunda.infrastructure.identity;

import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.GroupQuery;
import org.camunda.bpm.engine.impl.AbstractQuery;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;

import java.util.List;
import java.util.stream.Collectors;

public class CustomGroupQuery extends AbstractQuery<GroupQuery, Group> implements GroupQuery {
    
    private final GroupService roleService;
    private String groupId;
    private String groupName;
    private String userId;
    
    public CustomGroupQuery(GroupService roleService) {
        this.roleService = roleService;
    }
    
    @Override
    public GroupQuery groupId(String groupId) {
        this.groupId = groupId;
        return this;
    }
    
    @Override
    public GroupQuery groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    /**
     * Only select {@link Group}s where the name matches the given parameter.
     * The syntax to use is that of SQL, eg. %activiti%.
     *
     * @param groupNameLike
     */
    @Override
    public GroupQuery groupNameLike(String groupNameLike) {
        return null;
    }

    @Override
    public GroupQuery groupMember(String userId) {
        this.userId = userId;
        return this;
    }

    /**
     * Only select {@link Group}S that are potential starter for the given process definition.
     *
     * @param procDefId
     */
    @Override
    public GroupQuery potentialStarter(String procDefId) {
        return null;
    }

    /**
     * Only select {@link Group}s that belongs to the given tenant.
     *
     * @param tenantId
     */
    @Override
    public GroupQuery memberOfTenant(String tenantId) {
        return null;
    }

    /**
     * Order by group id (needs to be followed by {@link #asc()} or {@link #desc()}).
     */
    @Override
    public GroupQuery orderByGroupId() {
        return null;
    }

    /**
     * Order by group name (needs to be followed by {@link #asc()} or {@link #desc()}).
     */
    @Override
    public GroupQuery orderByGroupName() {
        return null;
    }

    /**
     * Order by group type (needs to be followed by {@link #asc()} or {@link #desc()}).
     */
    @Override
    public GroupQuery orderByGroupType() {
        return null;
    }

    @Override
    public long executeCount(CommandContext commandContext) {
        return roleService.countGroups(groupId, groupName, userId);
    }
    
    @Override
    public List<Group> executeList(CommandContext commandContext, Page page) {
        List<MainGroup> groups = roleService.findGroups(groupId, groupName, userId, page);
        return groups.stream().map(g -> (Group) g).collect(Collectors.toList());
    }
    
    // 其他必需的方法实现...
    @Override
    public GroupQuery groupIdIn(String... groupIds) {
        return this;
    }
    
    @Override
    public GroupQuery groupType(String groupType) {
        return this;
    }
    
    // ... 其他方法
}