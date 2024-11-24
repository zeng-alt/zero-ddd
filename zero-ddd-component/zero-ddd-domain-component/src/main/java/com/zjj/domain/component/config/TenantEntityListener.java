package com.zjj.domain.component.config;

import com.zjj.domain.component.BaseEntity;
import com.zjj.domain.component.TenantAuditable;
import com.zjj.domain.component.TenantAware;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月13日 21:25
 */
@Component
public class TenantEntityListener implements StatementInspector {
    private Optional<TenantAware<?>> tenantAware;
    private Method tenantByMethod;

    public TenantEntityListener() {
        tenantAware = Optional.empty();

        tenantByMethod = ReflectionUtils.findMethod(TenantAuditable.class, "setTenantBy");
    }

    public Method getTenantByMethod(Object o) {
        if(null == tenantByMethod){
            synchronized (this){
                if(null == tenantByMethod){
//                    AnnotatedElementUtils.findMergedAnnotation()
                    Field[] allFields = FieldUtils.getAllFields(o.getClass());
//                    AnnotationUtils.isAnnotationDeclaredLocally(TenantBy.class, allFields)
//                    AnnotatedElementUtils.getAllAnnotationAttributes()
                    tenantByMethod = ReflectionUtils.findMethod(TenantAuditable.class, "setTenantBy");
                }
            }
        }
        return tenantByMethod;
    }


    @Autowired
    public void setAuditorAware(TenantAware<?> tenantAware) {

        Assert.notNull(tenantAware, "TenantAware must not be null");
        this.tenantAware = Optional.of(tenantAware);
    }

    @PrePersist
    @PreUpdate
    @PreRemove
    @PostLoad
    public void preRemove(Object target) {
        Assert.notNull(target, "Entity must not be null");
        if (tenantAware.isEmpty()) {
            return;
        }
        if (target instanceof BaseEntity<?> baseEntity) {
            Object currentTenant = tenantAware.get().getCurrentTenant().orElse(null);
            Optional<Long> tenantBy = baseEntity.getTenantBy();
            if (tenantBy.isEmpty()) {
                return;
            }
            if (!tenantBy.get().equals(currentTenant)) {
                throw new EntityNotFoundException("The current tenant is not the same as the tenant of the entity.");
            }
        }
    }

    /**
     * Sets modification and creation date and auditor on the target object in case it implements {@link TenantAuditable} on
     * persist events.
     *
     * @param target
     */
//    @PrePersist
//    @PreUpdate
//    public void touchForCreate(Object target) {
//
//        Assert.notNull(target, "Entity must not be null");
//        if (tenantAware.isEmpty()) {
//            return;
//        }
//        if (target instanceof BaseEntity<?> baseEntity) {
//            baseEntity.setTenantBy((Long) tenantAware.get().getCurrentTenant().orElse(null));
//        }
//
//    }

    /**
     * Inspect the given SQL command, possibly returning a different
     * SQL command to be used instead. A {@code null} return value is
     * interpreted as if the method had returned its argument.
     *
     * @param sql The SQL to inspect
     * @return The processed SQL to use; may be {@code null}
     */
    @Override
    public String inspect(String sql) {

        return sql;
    }
}
