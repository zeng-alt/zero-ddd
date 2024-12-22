package com.zjj.exchange.tenant.api;

import com.zjj.exchange.tenant.sevice.RemoteTenantService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zengJiaJun
 * @crateTime 2024年12月21日 22:23
 * @version 1.0
 */
@RestController
@RequestMapping("/remote/tenant")
public interface RemoteTenantApi extends RemoteTenantService {
}
