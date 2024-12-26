package com.zjj.exchange.main.client;

import com.zjj.exchange.common.FeignApplicationConstant;
import com.zjj.exchange.main.factory.RemoteUserFallbackFactory;
import com.zjj.exchange.main.service.RemoteUserService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月25日 21:51
 */
@FeignClient(contextId = "remoteUserService", value = FeignApplicationConstant.MAIN, fallbackFactory = RemoteUserFallbackFactory.class, path = "/remote/user")
public interface RemoteUserClient extends RemoteUserService {
}
