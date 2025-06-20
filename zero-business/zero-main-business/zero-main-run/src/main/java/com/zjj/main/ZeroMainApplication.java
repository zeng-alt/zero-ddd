package com.zjj.main;


import com.zjj.autoconfigure.component.tenant.TenantMode;
import com.zjj.graphql.component.annotations.*;
import com.zjj.security.abac.component.annotation.EnableAbac;
import com.zjj.security.tenant.component.EnableTenantJwtCache;
import com.zjj.tenant.datasource.component.configuration.EnableMultiTenancy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.RollbackOn;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月14日 21:08
 */
@Slf4j
@EnableAbac
@EnableTenantJwtCache
@EnableTransactionManagement(order = 0, rollbackOn = RollbackOn.ALL_EXCEPTIONS)
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

//        List<HttpResource> resources = new ArrayList<>();
//        RequestMappingHandlerMapping bean = (RequestMappingHandlerMapping) run.getBean("requestMappingHandlerMapping");
//        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry :  bean.getHandlerMethods().entrySet()) {
//            RequestMappingInfo info = entry.getKey();
//            HandlerMethod handlerMethod = entry.getValue();
//
//            PathPatternsRequestCondition patternsCondition = info.getPathPatternsCondition();
//
//            if (patternsCondition == null) continue;
//            Set<PathPattern> paths = patternsCondition.getPatterns();
//            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
//
//            if (methodsCondition == null) continue;
//            Set<RequestMethod> methods = methodsCondition.getMethods();
//            Method method = handlerMethod.getMethod();
//            Operation operation = method.getAnnotation(Operation.class);
//
//            for (PathPattern pathPattern : paths) {
//                String path = pathPattern.getPatternString();
//                if (!path.startsWith("/main")) {
//                    continue;
//                }
//                for (RequestMethod requestMethod : methods) {
//                    HttpResource resource = new HttpResource();
//                    resource.setPath(path);
//                    resource.setMethod(requestMethod.name());
//                    resource.setCode(method.getName());
//                    if (operation != null) {
//                        resource.setName(operation.summary());  // summary 即 name 字段
//                    }
//
//                    resource.setRedirect(null); // 你可按需设置
//                    resource.setEnable(true);   // 默认启用
//                    resources.add(resource);
//                }
//            }
//        }
//
//        run.getBean(HttpResourceDao.class).saveAll(resources);
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
