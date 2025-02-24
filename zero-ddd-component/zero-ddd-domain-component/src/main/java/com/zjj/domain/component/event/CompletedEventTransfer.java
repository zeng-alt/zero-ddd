package com.zjj.domain.component.event;

import org.springframework.context.event.EventListener;
import org.springframework.modulith.moments.DayHasPassed;

/**
 * @author zengJiaJun
 * @crateTime 2025年02月24日 20:46
 * @version 1.0
 */
public interface CompletedEventTransfer {

    @EventListener
    public void on(DayHasPassed dayHasPassed);
}
