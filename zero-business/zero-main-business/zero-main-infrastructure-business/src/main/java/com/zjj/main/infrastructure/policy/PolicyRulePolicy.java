package com.zjj.main.infrastructure.policy;

import com.zjj.autoconfigure.component.security.abac.AbacCacheManage;
import com.zjj.autoconfigure.component.security.abac.PolicyRule;
import com.zjj.main.domain.rule.event.InitPolicyRuleEvent;
import com.zjj.main.infrastructure.db.jpa.dao.PermissionRuleEntityDao;
import com.zjj.main.infrastructure.db.jpa.entity.PermissionRuleEntity;
import com.zjj.main.infrastructure.db.jpa.entity.PolicyRuleEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月10日 15:01
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PolicyRulePolicy {

//    private final ResourceDao resourceDao;
    private final PermissionRuleEntityDao permissionRuleEntityDao;
    private final AbacCacheManage abacCacheManage;

    @Async
    @EventListener
    @Transactional(readOnly = true)
    public void on(InitPolicyRuleEvent event) {

        Map<String, Map<Boolean, Set<PolicyRule>>> collect = permissionRuleEntityDao.findAllByRulesNotEmpty().collect(Collectors.toMap(PermissionRuleEntity::getCode, r -> {
            Set<PolicyRuleEntity> rules = r.getRules();
            return rules.stream().collect(Collectors.groupingBy(PolicyRuleEntity::getPreAuth, Collectors.mapping(p -> {

                PolicyRule policyRule = new PolicyRule();
                policyRule.setCondition(p.getCondition());
                policyRule.setName(p.getName());
                policyRule.setDescription(p.getDescription());
                policyRule.setEnable(p.getEnable());
                return policyRule;
            }, Collectors.toSet())));
        }));

        Map<String, PolicyRule> prePolicyRule = new HashMap<>();
        Map<String, PolicyRule> postPolicyRule = new HashMap<>();
        for (Map.Entry<String, Map<Boolean, Set<PolicyRule>>> entity : collect.entrySet()) {
            for (Map.Entry<Boolean, Set<PolicyRule>> setEntry : entity.getValue().entrySet()) {
                if (CollectionUtils.isEmpty(setEntry.getValue())) {
                    continue;
                }
                if (Boolean.TRUE.equals(setEntry.getKey())) {
                    prePolicyRule.put(entity.getKey(), setEntry.getValue().stream().findFirst().get());
                } else {
                    postPolicyRule.put(entity.getKey(), setEntry.getValue().stream().findFirst().get());
                }
            }
        }
        this.abacCacheManage.clear();
        this.abacCacheManage.batchPutRule(prePolicyRule, true);
        this.abacCacheManage.batchPutRule(postPolicyRule, false);
        log.info("初始化abac完成");


//        Map<String, Map<Boolean, Set<PolicyRule>>> collect = resourceDao.findAll().stream().collect(Collectors.toMap(Resource::getCode, r -> {
//            Set<PolicyRuleEntity> rules = r.getRules();
//            return rules.stream().collect(Collectors.groupingBy(PolicyRuleEntity::getPreAuth, Collectors.mapping(p -> {
//                PolicyRule policyRule = new PolicyRule();
//                policyRule.setCondition(p.getCondition());
//                policyRule.setName(p.getName());
//                policyRule.setDescription(p.getDescription());
//                return policyRule;
//            }, Collectors.toSet())));
//        }));
//
//        Map<String, PolicyRule> prePolicyRule = new HashMap<>();
//        Map<String, PolicyRule> postPolicyRule = new HashMap<>();
//        for (Map.Entry<String, Map<Boolean, Set<PolicyRule>>> entity : collect.entrySet()) {
//            for (Map.Entry<Boolean, Set<PolicyRule>> setEntry : entity.getValue().entrySet()) {
//                if (CollectionUtils.isEmpty(setEntry.getValue())) {
//                    continue;
//                }
//                if (Boolean.TRUE.equals(setEntry.getKey())) {
//                    prePolicyRule.put(entity.getKey(), setEntry.getValue().stream().findFirst().get());
//                } else {
//                    postPolicyRule.put(entity.getKey(), setEntry.getValue().stream().findFirst().get());
//                }
//            }
//        }
//
//        this.abacCacheManage.batchPutRule(prePolicyRule, true);
//        this.abacCacheManage.batchPutRule(postPolicyRule, false);
//        log.info("初始化abac完成");
    }
}
