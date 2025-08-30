package com.zjj.camunda.infrastructure.identity;

import org.camunda.bpm.engine.identity.Tenant;
import org.camunda.bpm.engine.identity.TenantQuery;
import org.camunda.bpm.engine.impl.AbstractQuery;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;

import java.util.Collections;
import java.util.List;

public class CustomTenantQuery extends AbstractQuery<TenantQuery, Tenant> implements TenantQuery {
    
    @Override
    public long executeCount(CommandContext commandContext) {
        return 0;
    }
    
    @Override
    public List<Tenant> executeList(CommandContext commandContext, Page page) {
        return Collections.emptyList();
    }
    
    @Override
    public TenantQuery tenantId(String tenantId) {
        return this;
    }
    
    @Override
    public TenantQuery tenantIdIn(String... tenantIds) {
        return this;
    }
    
    @Override
    public TenantQuery tenantName(String tenantName) {
        return this;
    }
    
    @Override
    public TenantQuery tenantNameLike(String tenantNameLike) {
        return this;
    }

    /**
     * Only select {@link Tenant}s where the given user is member of.
     *
     * @param userId
     */
    @Override
    public TenantQuery userMember(String userId) {
        return this;
    }

    /**
     * Only select {@link Tenant}s where the given group is member of.
     *
     * @param groupId
     */
    @Override
    public TenantQuery groupMember(String groupId) {
        return this;
    }

    /**
     * Selects the {@link Tenant}s which belongs to one of the user's groups.
     * Can only be used in combination with {@link #userMember(String)}
     *
     * @param includingGroups
     */
    @Override
    public TenantQuery includingGroupsOfUser(boolean includingGroups) {
        return this;
    }

    /**
     * Order by tenant id (needs to be followed by {@link #asc()} or {@link #desc()}).
     */
    @Override
    public TenantQuery orderByTenantId() {
        return this;
    }

    /**
     * Order by tenant name (needs to be followed by {@link #asc()} or {@link #desc()}).
     */
    @Override
    public TenantQuery orderByTenantName() {
        return this;
    }
}