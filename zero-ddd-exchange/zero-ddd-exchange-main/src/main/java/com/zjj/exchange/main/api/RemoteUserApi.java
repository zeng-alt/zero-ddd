package com.zjj.exchange.main.api;

import com.zjj.exchange.main.service.RemoteUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月25日 21:47
 */
@RestController
@RequestMapping("/remote/user")
public interface RemoteUserApi extends RemoteUserService {
}
