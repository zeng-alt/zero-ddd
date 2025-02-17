package com.zjj.domain.component.command;

import org.jmolecules.architecture.cqrs.QueryModel;

/**
 * @author zengJiaJun
 * @crateTime 2025年02月17日 21:15
 * @version 1.0
 */
public class CommandHelper {

    private static CommandBus commandBus;


    public static void emit(Object command) {
        commandBus.emit(command);
    }
}
