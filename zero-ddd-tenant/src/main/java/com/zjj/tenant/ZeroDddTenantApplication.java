package com.zjj.tenant;

import com.google.common.collect.Sets;
import com.zjj.autoconfigure.component.security.rbac.GraphqlResource;
import com.zjj.autoconfigure.component.security.rbac.HttpResource;
import com.zjj.autoconfigure.component.security.rbac.RbacCacheManage;
import com.zjj.graphql.component.annotations.*;
import com.zjj.security.abac.component.annotation.EnableAbac;
import com.zjj.security.tenant.component.EnableTenantJwtCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.RollbackOn;

import java.util.Map;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月29日 21:34
 */
@Slf4j
@EnableAbac
@EnableTransactionManagement(order = 0, rollbackOn = RollbackOn.ALL_EXCEPTIONS)
@EnableTenantJwtCache
@EnableFeignClients(basePackages = "com.zjj")
@EnableJpaRepositories(basePackages = "com.zjj")
@SpringBootApplication
@EnableGenEntityType
@EnableGenEntityInput
@EnableGenEntityQuery
@EnableGenEntityFuzzyQuery
@EnableGenEntityMutation
@EnableScheduling
//@EnablePersistentDomainEvents
public class ZeroDddTenantApplication {

    private static final String LOGO = """
                                                       ██      ██      ██         ██                                         ██
                                                      ░██     ░██     ░██        ░██                                        ░██
             ██████  █████  ██████  ██████            ░██     ░██     ░██       ██████  █████  ███████   ██████   ███████  ██████
            ░░░░██  ██░░░██░░██░░█ ██░░░░██ █████  ██████  ██████  ██████ █████░░░██░  ██░░░██░░██░░░██ ░░░░░░██ ░░██░░░██░░░██░
               ██  ░███████ ░██ ░ ░██   ░██░░░░░  ██░░░██ ██░░░██ ██░░░██░░░░░   ░██  ░███████ ░██  ░██  ███████  ░██  ░██  ░██
              ██   ░██░░░░  ░██   ░██   ░██      ░██  ░██░██  ░██░██  ░██        ░██  ░██░░░░  ░██  ░██ ██░░░░██  ░██  ░██  ░██
             ██████░░██████░███   ░░██████       ░░██████░░██████░░██████        ░░██ ░░██████ ███  ░██░░████████ ███  ░██  ░░██
            ░░░░░░  ░░░░░░ ░░░     ░░░░░░         ░░░░░░  ░░░░░░  ░░░░░░          ░░   ░░░░░░ ░░░   ░░  ░░░░░░░░ ░░░   ░░    ░░
                        
            """;


//    @Bean
//    @Primary
//    public AbstractRoutingDataSource abstractRoutingDataSource(DataSource mydb) {
////        EmbeddedDatabase aDefault = createEmbeddedDatabase("default");
//        AbstractRoutingDataSource abstractRoutingDataSource = new AbstractRoutingDataSource() {
//
//            @Override
//            protected Object determineCurrentLookupKey() {
//                return null;
//            }
//        };
//
//        abstractRoutingDataSource.setDefaultTargetDataSource(mydb);
//        HashMap<Object, Object> targetDataSources = new HashMap<>();
//        targetDataSources.put("VMWARE", createEmbeddedDatabase("VMWARE"));
//        targetDataSources.put("PIVOTAL", createEmbeddedDatabase("PIVOTAL"));
//        abstractRoutingDataSource.setTargetDataSources(targetDataSources);
//        return abstractRoutingDataSource;
//    }
//
//    private EmbeddedDatabase createEmbeddedDatabase(String name) {
//
//        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName(name)
//                .build();
//    }
//
//    @Autowired
//    DataSourceProperties dataSourceProperties;
//
//    @Bean
//    public DataSource mydb() {
//        HikariDataSource dataSource = dataSourceProperties
//                .initializeDataSourceBuilder()
//                .type(HikariDataSource.class)
//                .build();
//        dataSource.setPoolName("masterDataSource");
//        return dataSource;
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//
//        Map<String, UserDetails> map = Map.of(
//                "root", SecurityUser.withUsername("root").password(passwordEncoder.encode("123456")).roles("admin").build(),
//                "user", SecurityUser.withUsername("user").password(passwordEncoder.encode("123456")).roles("user").build()
//        );
//
//        return new UserDetailsService() {
//            @Override
//            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                UserDetails userDetails = map.get(username);
//                if (userDetails == null) return userDetails;
//                return SecurityUser.withUserDetails(userDetails).build();
//            }
//        };
//    }

    @Autowired
    private RbacCacheManage rbacCacheManage;

    public static void main(String[] args) throws ClassNotFoundException {
        ConfigurableApplicationContext application = SpringApplication.run(ZeroDddTenantApplication.class, args);
        log.info(LOGO);
//        new SpringApplicationBuilder(ZeroDddTenantApplication.class)
//                .applicationStartup(new BufferingApplicationStartup(2048))
//                .run(args);

//        List<? extends Class<?>> list = application.getBeansWithAnnotation(Component.class).values().stream().map(Object::getClass).filter(a -> a.getName().startsWith("com.zjj")).toList();
//        for (Class<?> aClass : list) {
//            Method[] methods = aClass.getDeclaredMethods();
//            for (Method method : methods) {
//                Set<Annotation> allMergedAnnotations = AnnotatedElementUtils.findAllMergedAnnotations(method, Set.of(AbacPostAuthorize.class, AbacPreAuthorize.class));
//                if (!allMergedAnnotations.isEmpty()) {
//                    System.out.println(aClass.getName());
//                    // 拿到参数和返回值
//                    String[] parameterTypes = Arrays.stream(method.getParameterTypes()).map(Class::getName).toArray(String[]::new);
//                }
//            }
//        }

//        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
//        scanner.addIncludeFilter(new AnnotationTypeFilter(Component.class));
//        Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents("com.zjj");
//        for (BeanDefinition beanDefinition : beanDefinitions) {
//            Class<?> aClass = Class.forName(beanDefinition.getBeanClassName());
//            Method[] methods = aClass.getDeclaredMethods();
//            for (Method method : methods) {
//                Set<Annotation> allMergedAnnotations = AnnotatedElementUtils.findAllMergedAnnotations(method, Set.of(AbacPostAuthorize.class, AbacPreAuthorize.class));
//                if (!allMergedAnnotations.isEmpty()) {
//                    System.out.println(beanDefinition.getBeanClassName());
//                    // 拿到参数和返回值
//                    String[] parameterTypes = Arrays.stream(method.getParameterTypes()).map(Class::getName).toArray(String[]::new);
//                    String returnType = method.getReturnType().getName();
//                    System.out.println(parameterTypes);
//                    System.out.println(returnType);
//                }
//            }
//            Set<MethodMetadata> declaredMethods = ((ScannedGenericBeanDefinition) beanDefinition).getMetadata().getDeclaredMethods();
//            for (MethodMetadata methodMetadata : declaredMethods) {
//                MergedAnnotations annotations = methodMetadata.getAnnotations();
//                if (annotations.isPresent(AbacPreAuthorize.class) || annotations.isPresent(AbacPostAuthorize.class)) {
//                    System.out.println(beanDefinition.getBeanClassName());
//                    // 拿到参数和返回值
////                    String[] parameterTypes = methodMetadata.getParameterTypes().stream().map(Class::getName).toArray(String[]::new);
//                }
//            }
//
//
//        }
        System.out.println();


        //        ApplicationModules modules = ApplicationModules.of(ZeroDddTenantApplication.class);
//        modules.forEach(System.out::println);
//        modules.verify();
//        new Documenter(modules)
//                .writeModulesAsPlantUml()
//                .writeIndividualModulesAsPlantUml();
//        System.out.println(application);


    }

//    @Override
    public void run(String... args) throws Exception {
        HttpResource httpResource = new HttpResource();

        httpResource.setUri("/tenant/v1/menu/resource/{id}");
        httpResource.setMethod(HttpMethod.DELETE.name());
        rbacCacheManage.putHttpResource(Map.of("delete:menu:resource", httpResource));

        GraphqlResource graphqlResource = new GraphqlResource();
        graphqlResource.setMethod(HttpMethod.POST.name());
        graphqlResource.setUri("/tenant/graphql");
        graphqlResource.setFunctionName("findTenant");
        graphqlResource.setOperation("query");

        GraphqlResource graphqlResource2 = new GraphqlResource();
        graphqlResource2.setMethod(HttpMethod.POST.name());
        graphqlResource2.setUri("/tenant/graphql");
        graphqlResource2.setFunctionName("run");
        graphqlResource2.setOperation("query");

        rbacCacheManage.putGraphqlResource(Map.of("query:run", graphqlResource2, "query:findTenant", graphqlResource));
        rbacCacheManage.putRole(Map.of("admin", Sets.newHashSet("delete:menu:resource", "query:run", "query:findTenant")));
        rbacCacheManage.putRole(Map.of("user", Sets.newHashSet("query:run")));

    }
}
