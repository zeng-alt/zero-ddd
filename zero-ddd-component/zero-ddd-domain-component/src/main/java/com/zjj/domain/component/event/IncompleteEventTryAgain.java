package com.zjj.domain.component.event;

import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.IncompleteEventPublications;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.Task;

import java.time.Duration;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年01月13日 21:10
 */
@RequiredArgsConstructor
public abstract class IncompleteEventTryAgain implements SchedulingConfigurer {

    private final Duration interval;

    public abstract void tryAgain();

    /**
     * Callback allowing a {@link TaskScheduler}
     * and specific {@link Task} instances
     * to be registered against the given the {@link ScheduledTaskRegistrar}.
     *
     * @param taskRegistrar the registrar to be configured
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addFixedDelayTask(this::tryAgain, interval);
    }
}
