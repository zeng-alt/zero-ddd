package com.zjj.core.component.async;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月03日 11:20
 */
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.core.task.support.CompositeTaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.concurrent.Executor;

@RequiredArgsConstructor
public class AsyncConfig implements AsyncConfigurer {

    private final List<TaskDecorator> taskDecoratorList;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(7);
        executor.setMaxPoolSize(42);
        executor.setQueueCapacity(11);
        executor.setThreadNamePrefix("TenantAwareTaskExecutor-");
        executor.setTaskDecorator(new CompositeTaskDecorator(taskDecoratorList));
        executor.initialize();

        return executor;
    }

}
