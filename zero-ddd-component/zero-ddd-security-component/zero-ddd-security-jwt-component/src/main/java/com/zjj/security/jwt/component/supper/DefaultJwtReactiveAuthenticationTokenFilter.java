package com.zjj.security.jwt.component.supper;

import com.zjj.autoconfigure.component.security.SecurityUser;
import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtHelper;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import com.zjj.security.jwt.component.JwtDetail;
import com.zjj.security.jwt.component.JwtReactiveAuthenticationTokenFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月26日 21:04
 */
public class DefaultJwtReactiveAuthenticationTokenFilter extends JwtReactiveAuthenticationTokenFilter {

    public DefaultJwtReactiveAuthenticationTokenFilter(JwtHelper jwtHelper, JwtCacheManage jwtCacheManage, JwtProperties jwtProperties) {
        super(jwtHelper, jwtCacheManage, jwtProperties);
    }

    @Override
    public @NonNull Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        String token = resolveToken(exchange.getRequest());
        if (StringUtils.hasText(token)) {
            Map<String, Object> claims = jwtHelper.getClaimsFromToken(token);
            String soleId = (String) jwtHelper.getClaim(claims);

            UserDetails user = jwtCacheManage.get(soleId);

            if (user == null) {
                throw new BadCredentialsException("用户登录时间过期，重新登录");
            }

            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                    .header(jwtProperties.getFastToken(), soleId)
                    .build();


            // Create a new exchange with the modified request
            ServerWebExchange modifiedExchange = exchange.mutate()
                    .request(modifiedRequest)
                    .build();

            LocalDateTime expire = LocalDateTime.now();
            if (user instanceof SecurityUser securityUser) {
                expire = securityUser.getExpire();
            }
            modifiedExchange.getAttributes().put(DefaultJwtRenewFilter.RENEW_KEY,
                    JwtDetail.builder().id(soleId).user(user).expire(expire).build());

            return Mono.fromCallable(() -> UsernamePasswordAuthenticationToken.authenticated(user, null, user.getAuthorities()))
                    .subscribeOn(Schedulers.boundedElastic())
                    .flatMap(authentication -> chain.filter(modifiedExchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication)));
        }
        return chain.filter(exchange);
    }

    private String resolveToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(jwtHelper.tokenHeader());
        if (StringUtils.hasText(bearerToken)) {
            return bearerToken;
        }
        return null;
    }
}
