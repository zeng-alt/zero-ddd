package com.zjj.domain.component.command;


import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.util.Assert;

/**
 * @author zengJiaJun
 * @crateTime 2025年02月17日 21:20
 * @version 1.0
 */
@RequiredArgsConstructor
public class SimpleCommandBus implements CommandBus {

    private final CommandDispatcher commandDispatcher;

    @Override
    public void emit(Object command) {
        Assert.notNull(command, "Command must not be null");
        ApplicationEvent applicationEvent;
        if (command instanceof ApplicationEvent applEvent) {
            applicationEvent = applEvent;
        }
        else {
            applicationEvent = new PayloadApplicationEvent<>(this, command, null);
        }

        this.commandDispatcher.dispatches(applicationEvent);
    }
}
