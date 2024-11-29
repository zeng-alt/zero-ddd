package com.zjj.domain.component;

import com.zjj.autoconfigure.component.graphql.ExcludeTypeProvider;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月25日 21:26
 */
@Component
public class ExcludeEventPublicationProvider implements ExcludeTypeProvider {
    @Override
    public Set<String> exclude() {
        return Set.of("JpaEventPublication");
    }
}
