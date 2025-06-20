package com.zjj.main.domain.rule;

import com.zjj.main.domain.rule.cmd.StockInPolicyRuleCmd;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.cqrs.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月16日 09:43
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PolicyRuleHandler {

    private final PolicyRuleRepository repository;
    private final PolicyRuleFactory policyRuleFactory;

    @CommandHandler
    public void handler(StockInPolicyRuleCmd cmd) {
        policyRuleFactory.create(cmd);
    }
}
