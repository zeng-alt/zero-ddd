package com.zjj.domain.component.event;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年01月13日 17:03
 */
@Data
@Component
@ConfigurationProperties("spring.modulith.events.try")
public class EventTryAgainProperties {
    private Duration interval = Duration.ofSeconds(10);
    private Duration beforeDuration = Duration.ofSeconds(30);
}
