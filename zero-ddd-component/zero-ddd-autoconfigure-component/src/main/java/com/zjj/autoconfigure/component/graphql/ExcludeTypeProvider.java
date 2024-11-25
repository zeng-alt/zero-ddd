package com.zjj.autoconfigure.component.graphql;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月25日 21:24
 */
public interface ExcludeTypeProvider {

    public Set<String> exclude();
}
