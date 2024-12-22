package com.zjj.exchange.tenant.sevice;

import com.zjj.exchange.tenant.domain.Tenant;
import io.vavr.control.Option;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


/**
 * @author zengJiaJun
 * @crateTime 2024年12月21日 22:18
 * @version 1.0
 */
public interface RemoteTenantService {

    @GetMapping("/{id}")
    public Option<Tenant> findById(@PathVariable("id") String id);

    @GetMapping
    public List<Tenant> findAll();
}
