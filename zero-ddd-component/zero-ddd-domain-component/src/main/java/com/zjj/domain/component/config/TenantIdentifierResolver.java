package com.zjj.domain.component.config;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.TenantIdentifierMismatchException;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver<Long>, HibernatePropertiesCustomizer {

  @Override 
  public Long resolveCurrentTenantIdentifier () {

      return 1L;
  }

    /**
     * Should we validate that the tenant identifier of a "current sessions" that
     * already exists when {@link CurrentSessionContext#currentSession()} is called
     * matches the value returned here from {@link #resolveCurrentTenantIdentifier()}?
     *
     * @return {@code true} indicates that the extra validation will be performed;
     * {@code false} indicates it will not.
     * @see TenantIdentifierMismatchException
     */
    @Override
    public  boolean validateExistingCurrentSessions() {
        return true;
    }

    @Override
  public  void  customize (Map<String, Object> hibernateProperties) {
    hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this );
  } 
}