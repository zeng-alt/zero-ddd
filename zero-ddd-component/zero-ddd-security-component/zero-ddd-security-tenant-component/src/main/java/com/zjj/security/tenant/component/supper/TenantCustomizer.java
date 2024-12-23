//package com.zjj.security.tenant.component.supper;
//
//import com.zjj.autoconfigure.component.security.SecurityBuilderCustomizer;
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.Ordered;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
///**
// * @author zengJiaJun
// * @version 1.0
// * @crateTime 2024年12月23日 14:14
// */
//@RequiredArgsConstructor
//public class TenantCustomizer implements SecurityBuilderCustomizer, Ordered {
//
//    private final TenantHeaderFilter tenantHeaderFilter;
//    private final TenantWitchDataSourceFilter tenantWitchDataSourceFilter;
//
//    @Override
//    public void customize(HttpSecurity http) throws Exception {
//        http.addFilterBefore(tenantHeaderFilter, UsernamePasswordAuthenticationFilter.class);
//        http.addFilterAfter(tenantWitchDataSourceFilter, AnonymousAuthenticationFilter.class);
//    }
//
//    @Override
//    public int getOrder() {
//        return 10;
//    }
//}
