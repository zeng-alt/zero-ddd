package com.zjj.domain.component.event;

import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.CompletedEventPublications;
import org.springframework.modulith.moments.DayHasPassed;

import java.time.Duration;

/**
 * @author zengJiaJun
 * @crateTime 2025年02月24日 20:47
 * @version 1.0
 */
@RequiredArgsConstructor
public class DefaultCompletedEventTransfer implements CompletedEventTransfer {

    private final CompletedEventPublications completedEventPublications;

    @Override
    public void on(DayHasPassed dayHasPassed) {
        completedEventPublications.deletePublicationsOlderThan(Duration.ofDays(1));
    }
}
