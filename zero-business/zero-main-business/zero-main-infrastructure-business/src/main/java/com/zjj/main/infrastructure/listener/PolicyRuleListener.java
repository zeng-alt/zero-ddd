package com.zjj.main.infrastructure.listener;

import com.zjj.autoconfigure.component.security.abac.AbacCacheManage;
import com.zjj.autoconfigure.component.security.abac.PolicyRule;
import com.zjj.bean.componenet.BeanHelper;
import com.zjj.main.domain.rule.event.StockInPolicyRuleEvent;
import com.zjj.main.infrastructure.db.jpa.dao.PermissionRuleEntityDao;
import com.zjj.main.infrastructure.db.jpa.dao.PolicyRuleEntityDao;
import com.zjj.main.infrastructure.db.jpa.entity.PermissionRuleEntity;
import com.zjj.main.infrastructure.db.jpa.entity.PolicyRuleEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月16日 10:32
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PolicyRuleListener {

    private final PolicyRuleEntityDao policyRuleDao;
    private final PermissionRuleEntityDao  permissionRuleDao;
    private final AbacCacheManage abacCacheManage;

    @ApplicationModuleListener
    public void on(StockInPolicyRuleEvent event) {
        if (event.getId() == null) {
            PermissionRuleEntity permissionRuleEntity = permissionRuleDao.findById(event.getPermissionId()).getOrElseThrow(() -> new RuntimeException("权限不存在"));
            PolicyRuleEntity policyRuleEntity = BeanHelper.copyToObject(event, PolicyRuleEntity.class);
            policyRuleEntity.setPermission(permissionRuleEntity);
            policyRuleDao.save(policyRuleEntity);
            PolicyRule policyRule = BeanHelper.copyToObject(event, PolicyRule.class);
            abacCacheManage.putRule(permissionRuleEntity.getCode(), policyRule, event.getPreAuth());
            return;
        }


        PolicyRuleEntity policyRuleEntity = policyRuleDao
                .findById(event.getId())
                .map(entity -> {
                    BeanUtils.copyProperties(event, entity);
                    return entity;
                }).getOrElseThrow(() -> new RuntimeException("策略不存在"));

        policyRuleDao.save(policyRuleEntity);
        if (policyRuleEntity.getPermission() !=  null) {
            abacCacheManage.deleteRule(policyRuleEntity.getPermission().getCode(), event.getPreAuth());
        }
//        if (policyRuleEntity.getPermission() !=  null) {
//            Executors.newSingleThreadScheduledExecutor().schedule(() -> {
//                abacCacheManage.deleteRule(policyRuleEntity.getPermission().getCode(), event.getPreAuth()); // 再次删除缓存，防止并发读写覆盖
//            }, 500, TimeUnit.MILLISECONDS);
//        }
    }
}
