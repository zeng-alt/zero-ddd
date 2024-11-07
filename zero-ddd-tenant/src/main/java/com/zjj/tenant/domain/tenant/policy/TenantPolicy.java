package com.zjj.tenant.domain.tenant.policy;

import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.tenant.domain.tenant.event.CreateTenantDataSourceEvent;
import com.zjj.tenant.domain.tenant.event.UpdateTenantMenuEvent;
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


    @EventListener(value = UpdateTenantMenuEvent.class)
    public void handler(UpdateTenantMenuEvent event) {
        System.out.println("event");
        repository.put("event:" + event.getId(), event);
    }

    @EventListener(value = CreateTenantDataSourceEvent.class)
    public void handler(CreateTenantDataSourceEvent event) {
        System.out.println("event");
        repository.put("event:" + event.getId(), event);
    }
}
