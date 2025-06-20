package com.zjj.tenant.infrastructure.db.mutation;

import com.zjj.core.component.crypto.EncryptionService;
import com.zjj.graphql.component.spi.EntitySaveHandler;
import com.zjj.tenant.infrastructure.db.entity.TenantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月03日 21:29
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TenantEntitySaveHandler implements EntitySaveHandler<TenantEntity> {

    private final EncryptionService encryptionService;

    @Override
    public void handler(TenantEntity entity) {
        if (!entity.isNew()) {
            return;
        }

        if (entity.getTenantDataSource() != null && StringUtils.hasText(entity.getTenantDataSource().getPassword())) {
            entity.getTenantDataSource().setPassword(encryptionService.encrypt(entity.getTenantDataSource().getPassword()));
        }
    }
}
