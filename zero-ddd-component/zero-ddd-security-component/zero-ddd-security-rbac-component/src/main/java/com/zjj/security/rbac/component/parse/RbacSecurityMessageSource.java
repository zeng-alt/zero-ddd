package com.zjj.security.rbac.component.parse;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月09日 21:50
 */
public class RbacSecurityMessageSource extends ResourceBundleMessageSource {

    public RbacSecurityMessageSource() {
        setBasename("rbac");
    }

    public static MessageSourceAccessor getAccessor() {
        return new MessageSourceAccessor(new RbacSecurityMessageSource());
    }
}
