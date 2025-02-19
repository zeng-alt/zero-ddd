package com.zjj.domain.component.command;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author zengJiaJun
 * @crateTime 2025年02月17日 21:26
 * @version 1.0
 */
public interface CommandDispatcher {

    public void addApplicationListener(String key, ApplicationListener<?> listener);

    public void removeApplicationListener(String key);

    public void dispatches(ApplicationEvent command);
}
