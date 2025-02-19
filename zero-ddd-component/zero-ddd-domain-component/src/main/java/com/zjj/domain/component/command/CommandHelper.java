package com.zjj.domain.component.command;

/**
 * @author zengJiaJun
 * @crateTime 2025年02月17日 21:15
 * @version 1.0
 */
public class CommandHelper {

    private CommandHelper() {}

    private static CommandBus commandBus;

    public static void emit(Object command) {
        commandBus.emit(command);
    }
}
