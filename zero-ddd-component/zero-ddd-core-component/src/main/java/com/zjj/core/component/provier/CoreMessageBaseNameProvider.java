package com.zjj.core.component.provier;

import com.zjj.i18n.component.config.MessageBaseNameProvider;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月07日 21:45
 */
public class CoreMessageBaseNameProvider implements MessageBaseNameProvider {
    @Override
    public String[] getMessageBaseName() {
        return new String[] {"core"};
    }
}
