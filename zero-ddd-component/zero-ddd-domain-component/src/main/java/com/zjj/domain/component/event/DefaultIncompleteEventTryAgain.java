package com.zjj.domain.component.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.EventPublication;
import org.springframework.modulith.events.IncompleteEventPublications;
import org.springframework.modulith.moments.HourHasPassed;


import java.time.Duration;
import java.util.function.Predicate;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年01月13日 21:51
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultIncompleteEventTryAgain implements IncompleteEventTryAgain {

    private final IncompleteEventPublications incompleteEventPublications;

    @Override
    public void on(HourHasPassed hourHasPassed) {
        incompleteEventPublications.resubmitIncompletePublications(event -> true);
    }
}
