package com.zjj.main.domain.rule;

import com.zjj.bean.componenet.ApplicationContextHelper;
import com.zjj.bean.componenet.BeanHelper;
import com.zjj.main.domain.permission.PermissionId;
import com.zjj.main.domain.rule.cmd.StockInPolicyRuleCmd;
import com.zjj.main.domain.rule.event.StockInPolicyRuleEvent;
import io.vavr.control.Option;
import org.jmolecules.ddd.types.Association;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月16日 10:06
 */
@Component
public record PolicyRuleFactory(PolicyRuleRepository policyRuleRepository) {

    public Option<PolicyRule> create(StockInPolicyRuleCmd cmd) {
        Option<PolicyRule> policyRules = policyRuleRepository
                .findById(cmd.getId())
                .map(policyRule -> {
                    BeanUtils.copyProperties(cmd, policyRule);
                    return policyRule;
                })
                .orElse(() -> {
                    PolicyRuleAgg policyRule = BeanHelper.copyToObject(cmd, PolicyRuleAgg.class);
                    policyRule.setPermission(Association.forId(PermissionId.of(cmd.getPermissionId())));
                    return Option.of(policyRule);
                });

        StockInPolicyRuleEvent event = BeanHelper.copyToObject(cmd, StockInPolicyRuleEvent.class);
        ApplicationContextHelper.publisher().publishEvent(event);

        return policyRules;
    }
}
