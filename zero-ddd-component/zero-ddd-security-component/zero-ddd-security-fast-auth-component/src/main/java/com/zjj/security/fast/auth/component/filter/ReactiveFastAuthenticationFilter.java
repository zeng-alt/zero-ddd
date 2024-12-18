package com.zjj.security.fast.auth.component.filter;

import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月28日 21:07
 */
@RequiredArgsConstructor
public class ReactiveFastAuthenticationFilter implements WebFilter {

    protected final JwtCacheManage jwtCacheManage;
    protected final JwtProperties jwtProperties;

    @Override
    public @NonNull Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        List<String> list = exchange.getRequest().getHeaders().get(jwtProperties.getFastToken());
        if (CollectionUtils.isEmpty(list)) {
            return chain.filter(exchange);
        }
        String soleId = list.get(0);
        if (StringUtils.hasText(soleId)) {
            UserDetails user = jwtCacheManage.get(soleId);
            if (user == null) {
                throw new BadCredentialsException("用户登录时间过期，重新登录");
            }

            return Mono.fromCallable(() -> new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()))
                    .subscribeOn(Schedulers.boundedElastic())
                    .flatMap(authentication -> chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication)));
        }

        return chain.filter(exchange);
    }
}
