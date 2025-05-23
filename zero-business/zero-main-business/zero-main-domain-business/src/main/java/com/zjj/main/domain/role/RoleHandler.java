package com.zjj.main.domain.role;

import com.zjj.main.domain.role.cmd.*;
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

    private final RoleFactory roleFactory;
    private final RoleRepository roleRepository;

    @CommandHandler
    public void handler(StockInRoleCmd cmd) {
        this.roleFactory.create(cmd).forEach(r -> r.stockIn(cmd));
    }

    @CommandHandler
    public void handler(FunctionAuthorizeCmd cmd) {
        roleRepository.findAllByIdIn(cmd.getRoleIds()).forEach(r -> r.functionAuthorize(cmd));
    }

    @CommandHandler
    public void handler(FunctionCancelAuthorizeCmd cmd) {
        roleRepository.findAllByIdIn(cmd.getRoleIds()).forEach(r -> r.functionCancelAuthorize(cmd));
    }

    @CommandHandler
    public void handler(ServiceAuthorizeCmd cmd) {
        roleRepository.findAllByIdIn(cmd.getRoleIds()).forEach(r -> r.serviceAuthorize(cmd));
    }

    @CommandHandler
    public void handler(ServiceCancelAuthorizeCmd cmd) {
        roleRepository.findAllByIdIn(cmd.getRoleIds()).forEach(r -> r.serviceCancelAuthorize(cmd));
    }
}
