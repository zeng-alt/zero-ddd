//package com.zjj.gateway.filter;
//
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.context.annotation.Configuration;
//
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferUtils;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.BodyExtractors;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.nio.charset.StandardCharsets;
//
///**
// * @author zengJiaJun
// * @version 1.0
// * @crateTime 2024年11月28日 10:35
// */
//@Component
//public class DemoGrobalFilter implements GlobalFilter, Ordered {
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        // TODO 各种处理
//        //  .....
//        Flux<DataBuffer> body = exchange.getRequest().getBody();
//        DataBufferUtils.join(body).flatMap(buff -> {
//            byte[] bytes = new byte[buff.readableByteCount()];
//            buff.read(bytes);
//            DataBufferUtils.release(buff);
//            return Mono.just(new String(bytes, StandardCharsets.UTF_8));
//        }).doOnSuccess(s -> System.out.println(s)).subscribe();
//        System.out.println("hello");
//        return chain.filter(exchange);
//    }
//
//
//    @Override
//    public int getOrder() {
//        return -200;
//    }
//}
