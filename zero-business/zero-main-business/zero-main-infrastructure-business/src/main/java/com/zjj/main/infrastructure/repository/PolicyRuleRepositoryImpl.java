package com.zjj.main.infrastructure.repository;

import com.zjj.domain.component.DomainBeanHelper;
import com.zjj.main.domain.permission.PermissionId;
import com.zjj.main.domain.rule.PolicyRule;
import com.zjj.main.domain.rule.PolicyRuleAgg;
import com.zjj.main.domain.rule.PolicyRuleId;
import com.zjj.main.domain.rule.PolicyRuleRepository;
import com.zjj.main.infrastructure.db.jpa.dao.PolicyRuleEntityDao;
import io.vavr.control.Option;
import org.jmolecules.ddd.types.Association;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月16日 10:06
 */
@Component
public record PolicyRuleRepositoryImpl(
        PolicyRuleEntityDao policyRuleDao
) implements PolicyRuleRepository {

    @Override
    public Option<PolicyRule> findById(Long id) {
        return policyRuleDao
                .findById(id)
                .map(entity -> {
                    PolicyRuleAgg policyRuleAgg = DomainBeanHelper.copyToDomain(entity, PolicyRuleAgg.class, PolicyRuleId.class);
                    policyRuleAgg.setPermission(Association.forId(PermissionId.of(entity.getPermission().getId())));
                    return policyRuleAgg;
                });
    }
}
