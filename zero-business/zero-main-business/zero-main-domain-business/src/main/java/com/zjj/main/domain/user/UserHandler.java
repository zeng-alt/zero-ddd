package com.zjj.main.domain.user;

import com.zjj.i18n.component.BaseI18nException;
import com.zjj.main.domain.user.cmd.AssignRoleCmd;
import com.zjj.main.domain.user.cmd.StockInUserCmd;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.cqrs.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月24日 16:32
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserHandler {
    private final UserRepository userRepository;
    private final UserFactory userFactory;

    @CommandHandler
    public void handler(StockInUserCmd cmd) {
        userFactory.create(cmd);
    }

    @CommandHandler
    public void handler(AssignRoleCmd cmd) {
        if (!userRepository.existsByRoles(cmd.roleIds())) {
            throw new BaseI18nException("user.role.not.exists");
        }
        userRepository
                .findById(cmd.userId())
                .forEach(u -> u.assignRoles(cmd.roleIds()));
    }
}
