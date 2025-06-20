package com.zjj.tenant.interfaces.mvc.query;

import com.zjj.autoconfigure.component.core.ResponseEntity;
import com.zjj.tenant.application.service.TenantDataSourceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月03日 17:20
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/tenant/source")
public class TenantDataSourceQueryController {

    private final TenantDataSourceService tenantDataSourceService;

//    @Operation(summary = "测试数据源是否可连接")
//    @GetMapping("/test/{id}")
//    public String testDataSource(@PathVariable Long id) {
//        tenantDataSourceService.testDataSource(id);
//        return "ok";
//    }
}
