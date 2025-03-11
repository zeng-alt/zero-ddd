package com.zjj.cache.component;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

/**
 * @author zengJiaJun
 * @crateTime 2025年03月10日 21:48
 * @version 1.0
 */
@SpringBootApplication
public class TestCacheComponent {

    public static void main(String[] args) {
        new SpringApplicationBuilder(TestCacheComponent.class)
                .applicationStartup(new BufferingApplicationStartup(2048))
                .run(args);
    }
}
