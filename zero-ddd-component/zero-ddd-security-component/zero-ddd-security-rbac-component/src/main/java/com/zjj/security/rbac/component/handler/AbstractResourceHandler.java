package com.zjj.security.rbac.component.handler;

import com.zjj.security.rbac.component.manager.ResourceQueryManager;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月11日 21:49
 */
public abstract class AbstractResourceHandler implements ResourceHandler {

    public final ResourceQueryManager resourceQueryManager;

    protected AbstractResourceHandler(ResourceQueryManager resourceQueryManager) {
        this.resourceQueryManager = resourceQueryManager;
    }
}
