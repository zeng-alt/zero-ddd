package com.zjj.security.rbac.component.manager;

import com.zjj.autoconfigure.component.security.rbac.Resource;
import com.zjj.security.rbac.component.locator.ResourceLocator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月11日 21:50
 */
@RequiredArgsConstructor
public class ResourceQueryManager {

    private final List<ResourceLocator> resourceLocators;

    public List<Resource> query(Resource resource, Authentication authentication) {
        return resourceLocators
                .stream()
                .filter(locator -> locator.supports(resource.getClass()))
                .findFirst()
                .map(locator -> locator.load(authentication))
                .orElse(new ArrayList<>());
    }
}
