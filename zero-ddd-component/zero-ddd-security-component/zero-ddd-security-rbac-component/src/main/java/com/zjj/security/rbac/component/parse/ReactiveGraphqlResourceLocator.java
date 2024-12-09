package com.zjj.security.rbac.component.parse;

import com.zjj.autoconfigure.component.security.rbac.GraphqlResource;
import com.zjj.autoconfigure.component.security.rbac.Resource;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月06日 21:09
 */
public class ReactiveGraphqlResourceLocator extends AbstractReactiveResourceLocator {


    @Override
    protected List<Resource> list(Object o) {
        return new ArrayList<>();
    }

    @Override
    protected void verifyInstance(Resource resource) {
        Assert.isInstanceOf(GraphqlResource.class, resource,"Only GraphqlResource is supported");
    }

    @Override
    public boolean supports(Class<?> resource) {
        return (GraphqlResource.class.isAssignableFrom(resource));
    }


}
