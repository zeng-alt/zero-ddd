package com.zjj.domain.component.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.events.IncompleteEventPublications;
import org.springframework.modulith.moments.HourHasPassed;
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
public interface IncompleteEventTryAgain {


    @EventListener
    public void on(HourHasPassed hourHasPassed);

}
