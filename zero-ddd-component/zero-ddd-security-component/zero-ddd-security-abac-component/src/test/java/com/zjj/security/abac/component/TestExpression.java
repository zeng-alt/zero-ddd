package com.zjj.security.abac.component;

import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.*;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月16日 21:23
 */
public class TestExpression {

    private User user;
    private Map<String, Object> map = new ConcurrentHashMap<>();

    @BeforeEach
    public void init() {
        user = new User();
        user.setUsername("张三");
        user.setPassword("123456");
        user.setEmail("123456@qq.com");
        user.setPhone("123456789");
        user.setAddress("中国");
        user.setAge(18);

        map.put("date", LocalDateTime.now());
        map.put("user", user);
    }

    @Test
    public void test2() {
        System.out.println(System.currentTimeMillis());
        Mono<User> just = Mono.create(new Consumer<MonoSink<User>>() {
            @Override
            public void accept(MonoSink<User> userMonoSink) {

                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });

                future.orTimeout(1, TimeUnit.SECONDS).join();

                userMonoSink.success(user);
            }
        });
        System.out.println(System.currentTimeMillis());
        Mono<User> just1 = Mono.create(new Consumer<MonoSink<User>>() {
            @Override
            public void accept(MonoSink<User> userMonoSink) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });

                future.orTimeout(1, TimeUnit.SECONDS).join();
                userMonoSink.success(user);
            }
        });
        System.out.println(System.currentTimeMillis());
        Mono<User> just2 = Mono.create(new Consumer<MonoSink<User>>() {
            @Override
            public void accept(MonoSink<User> userMonoSink) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    // 这里可以放置你需要执行的代码
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });

                future.orTimeout(1, TimeUnit.SECONDS).join();
                userMonoSink.success(user);
            }
        });
        System.out.println(System.currentTimeMillis());
        just.subscribe(System.out::println);
        just1.subscribe(System.out::println);
        just2.subscribe(System.out::println);
        System.out.println(System.currentTimeMillis());
    }

    public User create() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Test
    void test1() {

        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression expression = expressionParser.parseExpression("hasAuthority('admin')");
        SimpleEvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().withRootObject(new Root()).withMethodResolvers(new CustomMethodResolver()).build();
//        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("user", user);
        context.setVariable("context", map);
//        context.setRootObject(new Root());
        Object value = expression.getValue(context);
        System.out.println(value);
    }

    public static class CustomMethodResolver implements MethodResolver {

        @Override
        public MethodExecutor resolve(EvaluationContext context, Object targetObject, String name, List<TypeDescriptor> argumentTypes) throws AccessException {
            if (targetObject instanceof Root) {
                Root root = (Root) targetObject;
                try {
                    Method method = root.getClass().getMethod(name, String.class);
                    if (method != null) {
                        return (context1, target, arguments) -> {
                            try {
                                return new TypedValue(method.invoke(target, arguments[0]));
                            } catch (Exception e) {
                                throw new AccessException("Error invoking method", e);
                            }
                        };
                    }
                } catch (NoSuchMethodException e) {
                    // Method not found, do nothing
                }
            }
            return null;
        }
    }

    @Data
    public static class Root {

        private String name = "1234";
        public final boolean hasAuthority(String authority) {
            return true;
        }
    }

    @Data
    public static class User {
        private String username;
        private String password;
        private String email;
        private String phone;
        private String address;
        private Integer age;
    }
}
