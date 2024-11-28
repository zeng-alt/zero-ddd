package com.zjj.gateway.filter;

import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 获取body请求数据（解决流不能重复读取问题）
 * 
 * @author ruoyi
 */
@Component
public class CacheRequestFilter implements WebFilter, Ordered {


    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // GET DELETE 不过滤
        HttpMethod method = exchange.getRequest().getMethod();
        if (method == HttpMethod.GET || method == HttpMethod.DELETE)
        {
            return chain.filter(exchange);
        }
        return ServerWebExchangeUtils.cacheRequestBodyAndRequest(exchange, (serverHttpRequest) -> {
            if (serverHttpRequest == exchange.getRequest())
            {
                return chain.filter(exchange);
            }
            return chain.filter(exchange.mutate().request(serverHttpRequest).build());
        });
    }
}