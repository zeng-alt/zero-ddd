package com.zjj.tenant.database.component.proovider;

import com.zjj.i18n.component.config.MessageBaseNameProvider;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:11
 */
@Component
public class TenantDatabaseMessageBaseNameProvider implements MessageBaseNameProvider {

    @Override
    public String[] getMessageBaseName() {
        return new String[] {"tenant-database"};
    }
}
