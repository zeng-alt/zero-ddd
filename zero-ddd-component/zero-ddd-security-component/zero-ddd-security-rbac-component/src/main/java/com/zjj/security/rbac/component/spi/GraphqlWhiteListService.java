package com.zjj.security.rbac.component.spi;


import com.zjj.autoconfigure.component.security.rbac.GraphqlResource;

import java.util.Set;

public interface GraphqlWhiteListService {

    public Set<GraphqlResource> getWhiteList();
}
