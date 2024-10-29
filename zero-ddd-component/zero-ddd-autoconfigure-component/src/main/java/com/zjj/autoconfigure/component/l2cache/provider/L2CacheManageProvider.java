package com.zjj.autoconfigure.component.l2cache.provider;

import com.zjj.autoconfigure.component.l2cache.L2CacheManage;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月25日 14:15
 */
public interface L2CacheManageProvider {

    void consumer(L2CacheManage l2CacheManage);
}
