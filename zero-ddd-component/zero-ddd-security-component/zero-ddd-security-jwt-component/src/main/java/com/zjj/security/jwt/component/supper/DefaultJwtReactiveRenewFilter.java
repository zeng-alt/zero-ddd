package com.zjj.security.jwt.component.supper;

import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.security.jwt.component.JwtDetail;
import com.zjj.security.jwt.component.JwtReactiveRenewFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月19日 21:08
 */
public class DefaultJwtReactiveRenewFilter extends JwtReactiveRenewFilter {

    public DefaultJwtReactiveRenewFilter(JwtCacheManage jwtCacheManage) {
        super(jwtCacheManage);
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        Object attribute = exchange.getAttribute(DefaultJwtRenewFilter.RENEW_KEY);
        if (Objects.nonNull(attribute)) {
            JwtDetail jwtDetail = (JwtDetail) attribute;
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expire = jwtDetail.getExpire();
            // 如果过期时间小当前时间的前15分钟，不进行刷新
            if (expire.isBefore(now.plusMinutes(15))) {
                jwtCacheManage.put(jwtDetail.getId(), jwtDetail.getUser());
            }
        }

        return chain.filter(exchange);
    }
}
