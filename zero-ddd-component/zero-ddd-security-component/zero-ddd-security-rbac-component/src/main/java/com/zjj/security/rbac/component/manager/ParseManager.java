package com.zjj.security.rbac.component.manager;

import com.zjj.security.rbac.component.handler.HttpResourceHandler;
import com.zjj.security.rbac.component.handler.ResourceHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月29日 21:14
 */
@RequiredArgsConstructor
public class ParseManager implements InitializingBean, MessageSourceAware {

    private final List<ResourceHandler> resourceHandlers;
    private final HttpResourceHandler httpResourceHandler;


    public ResourceHandler parse(HttpServletRequest request) {
        return resourceHandlers
                .stream()
                .filter(handler -> handler.matcher(request))
                .findFirst()
                .orElse(httpResourceHandler);
    }

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Override
    public final void afterPropertiesSet() throws Exception {
        Assert.notNull(this.messages, "A message source must be set");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

}
