package com.zjj.security.rbac.component.parse;

import com.zjj.security.rbac.component.domain.Resource;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月05日 17:14
 */
public interface ReactiveResourceQueryManager {

    public boolean authorize(Resource resource);
}
