package com.zjj.domain.component.command;

/**
 * @author zengJiaJun
 * @crateTime 2025年02月17日 21:26
 * @version 1.0
 */
public interface CommandDispatcher {

    public void dispatches(Object command);
}
