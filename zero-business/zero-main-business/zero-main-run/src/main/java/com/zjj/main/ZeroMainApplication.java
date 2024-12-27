package com.zjj.main;

import com.zjj.graphql.component.config.EnableGenEntityInput;
import com.zjj.graphql.component.config.EnableGenEntityQuery;
import com.zjj.graphql.component.config.EnableGenEntityType;
import com.zjj.i18n.component.MessageSourceHelper;
import com.zjj.tenant.datasource.component.EnableMultiTenancy;
import com.zjj.tenant.datasource.component.TenantMode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月14日 21:08
 */
@EnableMultiTenancy(mode = TenantMode.DATABASE)
@EnableJpaRepositories(basePackages = "com.zjj")
@SpringBootApplication
@EnableFeignClients(basePackages = "com.zjj")
@EnableGenEntityType
@EnableGenEntityInput
@EnableGenEntityQuery
//@EnableGenEntityFuzzyQuery
public class ZeroMainApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ZeroMainApplication.class, args);
        System.out.println(run);
    }
}
