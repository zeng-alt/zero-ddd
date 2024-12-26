package com.zjj.exchange.main.service;

import com.zjj.autoconfigure.component.security.SecurityUser;
import io.vavr.control.Option;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月25日21:46
 */
public interface RemoteUserService {

    @GetMapping("/{username}")
    public Option<SecurityUser> findByUsername(@PathVariable("username") String username);
}
