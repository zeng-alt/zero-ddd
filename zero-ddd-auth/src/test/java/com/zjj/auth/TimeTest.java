package com.zjj.auth;

import org.junit.jupiter.api.Test;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月29日 10:27
 */
public class TimeTest {

    @Test
    public void testTime() {
        TemporalUnit chronoUnit = ChronoUnit.HOURS;
        long totalMinutes = 20 * chronoUnit.getDuration().toMinutes();
        System.out.println(totalMinutes);
    }
}
