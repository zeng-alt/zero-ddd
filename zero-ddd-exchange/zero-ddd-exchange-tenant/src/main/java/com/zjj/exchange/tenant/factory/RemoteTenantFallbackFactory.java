package com.zjj.exchange.tenant.factory;

import com.zjj.autoconfigure.component.tenant.Tenant;
import com.zjj.exchange.tenant.client.RemoteTenantClient;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zengJiaJun
 * @crateTime 2024年12月21日 22:25
 * @version 1.0
 */
@Slf4j
@Component
public class RemoteTenantFallbackFactory implements FallbackFactory<RemoteTenantClient> {
    @Override
    public RemoteTenantClient create(Throwable cause) {
        return new RemoteTenantClient() {
            @Override
            public Option<Tenant> findById(String id) {
                log.error("根据{}获取租户信息失败:", id, cause);
                return Option.none();
            }

            @Override
            public List<Tenant> findAll() {
                log.error("获取租户信息失败:", cause);
                return List.of();
            }
        };
    }
}
