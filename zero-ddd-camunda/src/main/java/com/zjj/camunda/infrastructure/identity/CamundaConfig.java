package com.zjj.camunda.infrastructure.identity;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.identity.ReadOnlyIdentityProvider;
import org.camunda.bpm.engine.impl.interceptor.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
public class CamundaConfig {
    
    @Autowired
    private RbacIdentityService rbacIdentityService;
    
    @Bean
    public ProcessEnginePlugin customIdentityPlugin() {
        return new ProcessEnginePlugin() {
            @Override
            public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
//                Map<Class<?>, SessionFactory> sessionFactories = processEngineConfiguration.getSessionFactories();
//                if (sessionFactories == null) {
//                    sessionFactories = new HashMap<>();
//                    sessionFactories.put(ReadOnlyIdentityProvider.class, new CustomIdentityProviderSessionFactory(rbacIdentityService));
//                    processEngineConfiguration.setSessionFactories(sessionFactories);
//                } else {
//                    sessionFactories.put(ReadOnlyIdentityProvider.class, new CustomIdentityProviderSessionFactory(rbacIdentityService));
//                }


//                processEngineConfiguration.setIdentityProviderSessionFactory(
//                        new CustomIdentityProviderSessionFactory(rbacIdentityService)
//                );

                processEngineConfiguration.getCustomSessionFactories().add(new CustomIdentityProviderSessionFactory(rbacIdentityService));

                // 重要：设置为只读模式，避免调用写入方法
                processEngineConfiguration.setDbIdentityUsed(false);

                // 禁用默认身份服务的自动创建管理员用户
                processEngineConfiguration.setAuthorizationEnabled(false);
//                processEngineConfiguration.setCreateAdminUser(false);

                // 设置管理员用户ID（需要在你的RBAC系统中存在）
                processEngineConfiguration.setAdminUsers(List.of("admin", "superAdmin"));

                // 禁用数据库身份表的创建和使用
                processEngineConfiguration.setSkipHistoryOptimisticLockingExceptions(true);

                // 设置历史级别
//                processEngineConfiguration.setHistory("audit");

                // 设置作业执行器
                processEngineConfiguration.setJobExecutorActivate(true);

                log.info("配置自定义只读身份提供者");
            }
            
            @Override
            public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
                // 可选的后初始化逻辑
            }
            
            @Override
            public void postProcessEngineBuild(ProcessEngine processEngine) {
                log.info("ProcessEngine构建完成，验证身份提供者...");

                // 验证身份提供者是否正常工作
                try {
                    IdentityService identityService = processEngine.getIdentityService();
                    User testUser = identityService.createUserQuery().userId("admin").singleResult();
                    log.info("身份提供者验证成功，找到用户: {}", testUser != null ? testUser.getId() : "null");
                } catch (Exception e) {
                    log.error("身份提供者验证失败", e);
                }
            }
        };
    }
}