package com.zjj.security.rbac.component.supper;

import com.zjj.i18n.component.config.MessageBaseNameProvider;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月09日 21:03
 */
@Component
public class SecurityRbacMessageBaseNameProvider implements MessageBaseNameProvider {
    @Override
    public String[] getMessageBaseName() {
        return new String[] {"rbac"};
    }
}
