package com.zjj.domain.component.command;

import org.jmolecules.architecture.cqrs.CommandDispatcher;
import org.springframework.stereotype.Service;

/**
 * @author zengJiaJun
 * @crateTime 2025年02月17日 21:12
 * @version 1.0
 */
public interface CommandBus {


    public void emit(Object command);


}
