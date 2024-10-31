package com.zjj.tenant;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.zjj.graphql.component.config.EnableGenEntityFuzzyQuery;
import com.zjj.graphql.component.config.EnableGenEntityInput;
import com.zjj.graphql.component.config.EnableGenEntityQuery;
import com.zjj.graphql.component.config.EnableGenEntityType;
import com.zjj.tenant.domain.tenant.QTenant;
import com.zjj.tenant.domain.tenant.source.QTenantDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月29日 21:34
 */
@EnableWebSecurity(debug = true)
@EnableJpaRepositories(basePackages = "com.zjj")
@SpringBootApplication
@EnableGenEntityType
@EnableGenEntityInput
@EnableGenEntityQuery
@EnableGenEntityFuzzyQuery
public class ZeroDddTenantApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ZeroDddTenantApplication.class, args);
        QTenant tenant = QTenant.tenant;

        System.out.println(run);
    }
}
