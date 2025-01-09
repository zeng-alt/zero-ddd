package com.zjj.tenant;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.test.ApplicationModuleTest;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年01月02日 21:46
 */
@ApplicationModuleTest
class ZeroDDDTenantApplicationTest {

    @Test
    void contextLoads() {
        ApplicationModules.of(ZeroDddTenantApplication.class).verify();
    }
}
