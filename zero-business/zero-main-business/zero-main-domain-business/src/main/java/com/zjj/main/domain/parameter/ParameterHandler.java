package com.zjj.main.domain.parameter;

import com.zjj.bean.componenet.ApplicationContextHelper;
import com.zjj.main.domain.parameter.cmd.InitUserPasswordCmd;
import com.zjj.main.domain.user.event.UpdateUserPasswordEvent;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.cqrs.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月28日 14:12
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParameterHandler {

    private final ParameterRepository parameterRepository;

    @CommandHandler
    public void handler(InitUserPasswordCmd cmd) {
        parameterRepository
                .findByParameterKey("user.password.init")
                .map(p -> (String) p.getTargetValue())
                .orElse(Option.of("123456"))
                .forEach(password -> ApplicationContextHelper.publisher().publishEvent(UpdateUserPasswordEvent.of(cmd.userId(), password)));
    }
}
