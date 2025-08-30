package com.zjj.camunda.infrastructure.identity;

import org.camunda.bpm.engine.impl.identity.ReadOnlyIdentityProvider;
import org.camunda.bpm.engine.impl.interceptor.Session;
import org.camunda.bpm.engine.impl.interceptor.SessionFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomIdentityProviderSessionFactory implements SessionFactory {
    
    private final RbacIdentityService rbacIdentityService;
    
    public CustomIdentityProviderSessionFactory(RbacIdentityService rbacIdentityService) {
        this.rbacIdentityService = rbacIdentityService;
    }
    
    @Override
    public Class<?> getSessionType() {
        return ReadOnlyIdentityProvider.class;
    }

    @Override
    public Session openSession() {
        return rbacIdentityService;
    }
}
