package com.zjj.main;


import com.zjj.autoconfigure.component.security.rbac.GraphqlResource;
import com.zjj.autoconfigure.component.tenant.TenantMode;
import com.zjj.graphql.component.annotations.*;
import com.zjj.main.infrastructure.db.jpa.dao.GraphqlResourceDao;
import com.zjj.main.infrastructure.db.jpa.entity.GraphqlResourceEntity;
import com.zjj.security.abac.component.annotation.EnableAbac;
import com.zjj.security.rbac.client.component.GraphqlTemplateSupper;
import com.zjj.security.tenant.component.EnableTenantJwtCache;
import com.zjj.tenant.datasource.component.configuration.EnableMultiTenancy;
import com.zjj.tenant.management.component.annotations.EnableMasterJpaRepositories;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月14日 21:08
 */
@Slf4j
@EnableAbac
@EnableTenantJwtCache
//@EnableMultiTenancy(mode = TenantMode.MIXED)
@EnableMultiTenancy(mode = TenantMode.MIXED)
@SpringBootApplication
@EnableFeignClients(basePackages = "com.zjj")
@EnableGenEntityType
@EnableGenEntityInput
@EnableGenEntityQuery
@EnableGenEntityFuzzyQuery
@EnableGenEntityMutation
@EnableGenEntityConditionQuery
@EnableJpaRepositories(basePackages = "com.zjj.main")
@EntityScan(basePackages = {"com.zjj.main", "org.springframework.modulith.events.jpa"})
//@EnableMasterJpaRepositories
public class ZeroMainApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ZeroMainApplication.class, args);
        log.info(LOGO);
//        GraphqlTemplateSupper graphqlTemplateSupper = run.getBean(GraphqlTemplateSupper.class);
//        GraphqlResourceDao graphqlResourceDao = run.getBean(GraphqlResourceDao.class);
//        List<GraphqlResource> graphqlTemplate = graphqlTemplateSupper.getGraphqlTemplate();
//        List<GraphqlResourceEntity> list = graphqlTemplate.stream().map(g -> {
//            GraphqlResourceEntity entity = new GraphqlResourceEntity();
//            entity.setCode("Main" + StringUtils.capitalize(g.getFunctionName()));
//            entity.setName(g.getFunctionName());
//            entity.setUri(g.getUri());
//            entity.setOperation(g.getOperation());
//            entity.setFunctionName(g.getFunctionName());
//            return entity;
//        }).toList();
//        graphqlResourceDao.saveAll(list);
    }


    private static final String LOGO = """
                                                       ██      ██      ██            ██                                ██
                                                      ░██     ░██     ░██           ░██                               ░░
             ██████  █████  ██████  ██████            ░██     ░██     ░██           ░██  ██████  ██████████   ██████   ██ ███████
            ░░░░██  ██░░░██░░██░░█ ██░░░░██ █████  ██████  ██████  ██████ █████  ██████ ██░░░░██░░██░░██░░██ ░░░░░░██ ░██░░██░░░██
               ██  ░███████ ░██ ░ ░██   ░██░░░░░  ██░░░██ ██░░░██ ██░░░██░░░░░  ██░░░██░██   ░██ ░██ ░██ ░██  ███████ ░██ ░██  ░██
              ██   ░██░░░░  ░██   ░██   ░██      ░██  ░██░██  ░██░██  ░██      ░██  ░██░██   ░██ ░██ ░██ ░██ ██░░░░██ ░██ ░██  ░██
             ██████░░██████░███   ░░██████       ░░██████░░██████░░██████      ░░██████░░██████  ███ ░██ ░██░░████████░██ ███  ░██
            ░░░░░░  ░░░░░░ ░░░     ░░░░░░         ░░░░░░  ░░░░░░  ░░░░░░        ░░░░░░  ░░░░░░  ░░░  ░░  ░░  ░░░░░░░░ ░░ ░░░   ░░
                        
            """;
}
