package com.zjj.security.core.component.spi;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月27日 21:35
 */
public interface WhiteListService {

    public Set<String> getWhiteList();
}
