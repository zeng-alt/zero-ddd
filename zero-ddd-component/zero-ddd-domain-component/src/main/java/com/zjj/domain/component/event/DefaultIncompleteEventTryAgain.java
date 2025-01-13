package com.zjj.domain.component.event;


import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.IncompleteEventPublications;


import java.time.Duration;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年01月13日 21:51
 */
@Slf4j
public class DefaultIncompleteEventTryAgain extends IncompleteEventTryAgain {

    private final IncompleteEventPublications incompleteEventPublications;
    private final Duration beforeDuration;


    public DefaultIncompleteEventTryAgain(IncompleteEventPublications incompleteEventPublications, Duration beforeDuration, Duration interval) {
        super(interval);
        this.incompleteEventPublications = incompleteEventPublications;
        this.beforeDuration = beforeDuration;
    }


    public void tryAgain() {
        log.info("开启重试未完成事件!!!");
        incompleteEventPublications.resubmitIncompletePublicationsOlderThan(beforeDuration);
    }
}
