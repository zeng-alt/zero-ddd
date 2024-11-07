package com.zjj.security.core.component.supper;

import com.zjj.i18n.component.config.MessageBaseNameProvider;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月07日 21:45
 */
public class SecurityCoreMessageBaseNameProvider implements MessageBaseNameProvider {
    @Override
    public String[] getMessageBaseName() {
        return new String[] {"security-core"};
    }
}
