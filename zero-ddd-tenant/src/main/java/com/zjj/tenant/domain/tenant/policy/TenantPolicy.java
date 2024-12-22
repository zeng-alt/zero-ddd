package com.zjj.tenant.domain.tenant.policy;

import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.autoconfigure.component.redis.RedisSubPubRepository;
import com.zjj.cache.component.repository.impl.RedisTopicRepositoryImpl;
import com.zjj.exchange.tenant.domain.Tenant;
import com.zjj.tenant.domain.tenant.event.CreateTenantDataSourceEvent;
import com.zjj.tenant.domain.tenant.event.DisableTenantMenuEvent;
import com.zjj.tenant.domain.tenant.event.EnableTenantMenuEvent;
import com.zjj.tenant.domain.tenant.event.UpdateTenantMenuEvent;
import com.zjj.tenant.domain.tenant.TenantMenu;
import com.zjj.tenant.domain.tenant.TenantMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月04日 21:45
 */
@Component
@RequiredArgsConstructor
public class TenantPolicy {

    private final RedisStringRepository repository;
    private final RedisTopicRepositoryImpl redisSubPubRepository;
    private final TenantMenuRepository tenantMenuRepository;


    @EventListener(value = UpdateTenantMenuEvent.class)
    public void handler(UpdateTenantMenuEvent event) {
        System.out.println("event");
        repository.put("event:" + event.getId(), event);
    }

    @EventListener(value = CreateTenantDataSourceEvent.class)
    public void handler(CreateTenantDataSourceEvent event) {
        System.out.println("event");
        redisSubPubRepository.publish("tenant-channel", Tenant.builder().tenantId(event.getTenantKey()).db(event.getPoolName()).password(event.getPassword()).schema(event.getSchema()).build());
        repository.put("event:" + event.getId(), event);
    }


    @EventListener(value = EnableTenantMenuEvent.class)
    public void handler(EnableTenantMenuEvent event) {
        System.out.println("event");
        tenantMenuRepository
                .findById(event.getMenuId())
                .map(TenantMenu::enable)
                .map(tenantMenuRepository::save)
                .getOrElseThrow(() -> new IllegalArgumentException(event.getMenuId() + "租户菜单不存在"));
        repository.put("event:" + event.getId(), event);
    }

    @EventListener(value = DisableTenantMenuEvent.class)
    public void handler(DisableTenantMenuEvent event) {
        System.out.println("event");
        tenantMenuRepository
                .findById(event.getMenuId())
                .map(TenantMenu::disable)
                .map(tenantMenuRepository::save)
                .getOrElseThrow(() -> new IllegalArgumentException(event.getMenuId() + "租户菜单不存在"));
        repository.put("event:" + event.getId(), event);
    }
}
