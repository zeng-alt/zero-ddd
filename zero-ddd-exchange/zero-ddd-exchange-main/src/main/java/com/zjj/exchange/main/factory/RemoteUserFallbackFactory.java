package com.zjj.exchange.main.factory;

import com.zjj.autoconfigure.component.security.SecurityUser;
import com.zjj.exchange.main.client.RemoteUserClient;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月25日 21:52
 */
@Component
@Slf4j
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserClient> {
    @Override
    public RemoteUserClient create(Throwable cause) {
        return new RemoteUserClient() {
            @Override
            public Option<SecurityUser> findByUsername(String username) {
                log.error("根据{}获取用户信息失败:", username, cause);
                return Option.none();
            }
        };
    }
}
