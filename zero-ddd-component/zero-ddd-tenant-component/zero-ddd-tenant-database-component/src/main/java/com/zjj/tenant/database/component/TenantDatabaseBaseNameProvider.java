package com.zjj.tenant.database.component;

import com.zjj.i18n.component.config.MessageBaseNameProvider;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:11
 */
@Component
public class TenantDatabaseBaseNameProvider implements MessageBaseNameProvider {

    @Override
    public String[] getMessageBaseName() {
        return new String[] {"tenant-database"};
    }
}
