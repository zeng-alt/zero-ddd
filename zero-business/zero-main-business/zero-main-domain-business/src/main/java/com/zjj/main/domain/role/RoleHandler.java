package com.zjj.main.domain.role;

import com.zjj.main.domain.role.cmd.StockInRoleCmd;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.cqrs.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月24日 16:10
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleHandler {

    private final RoleRepository roleRepository;
    private final RoleFactory roleFactory;

    @CommandHandler
    public void handler(StockInRoleCmd cmd) {
        roleFactory.create(cmd).forEach(roleRepository::save);
    }
}
