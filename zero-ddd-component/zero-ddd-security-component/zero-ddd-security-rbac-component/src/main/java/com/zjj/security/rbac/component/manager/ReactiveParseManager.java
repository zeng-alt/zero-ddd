package com.zjj.security.rbac.component.manager;

import com.zjj.security.rbac.component.handler.ReactiveHttpResourceHandler;
import com.zjj.security.rbac.component.handler.ReactiveResourceHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月29日 21:15
 */
@Slf4j
@RequiredArgsConstructor
public class ReactiveParseManager implements InitializingBean, MessageSourceAware {


    private final List<ReactiveResourceHandler> reactiveResourceHandlers;
    private final ReactiveHttpResourceHandler reactiveHttpResourceHandler;


    public Mono<ReactiveResourceHandler> parse(ServerWebExchange exchange) {

        return Flux.fromIterable(this.reactiveResourceHandlers)
                .doOnNext((handler) -> log.debug(String.valueOf(LogMessage.format("Trying to match using %s", handler))))
                .publishOn(Schedulers.boundedElastic())
                .filter(reactiveResourceHandler -> Boolean.TRUE.equals(reactiveResourceHandler.matcher(exchange).map(ServerWebExchangeMatcher.MatchResult::isMatch).block()))
                .next()
                .switchIfEmpty(Mono.just(reactiveHttpResourceHandler));

//        return Flux.fromIterable(this.reactiveResourceHandlers)
//                .doOnNext((matcher) -> log.debug(String.valueOf(LogMessage.format("Trying to match using %s", matcher))))
//                .flatMap((matcher) -> matcher.matcher(exchange))
//                .filter(ServerWebExchangeMatcher.MatchResult::isMatch)
//                .flatMap(s -> s.)
//                .next()
//                .switchIfEmpty(Mono.just(reactiveHttpResourceHandler));
//        for (ReactiveResourceHandler reactiveResourceHandler : reactiveResourceHandlers) {
//            if (reactiveResourceHandler.matcher(exchange)) {
//                return Mono.just(reactiveResourceHandler);
//            }
//        }
//        return Mono.empty();
    }

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Override
    public final void afterPropertiesSet() throws Exception {
        Assert.notNull(this.messages, "A message source must be set");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
}
