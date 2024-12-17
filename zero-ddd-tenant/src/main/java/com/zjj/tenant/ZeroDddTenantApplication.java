package com.zjj.tenant;

import com.google.common.collect.Sets;
import com.zjj.autoconfigure.component.security.rbac.GraphqlResource;
import com.zjj.autoconfigure.component.security.rbac.HttpResource;
import com.zjj.autoconfigure.component.security.rbac.RbacCacheManage;
import com.zjj.bean.componenet.BeanHelper;
import com.zjj.graphql.component.config.EnableGenEntityFuzzyQuery;
import com.zjj.graphql.component.config.EnableGenEntityInput;
import com.zjj.graphql.component.config.EnableGenEntityQuery;
import com.zjj.graphql.component.config.EnableGenEntityType;
import com.zjj.security.abac.component.annotation.EnableAbac;
import com.zjj.security.core.component.domain.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月29日 21:34
 */
@EnableAbac
@EnableJpaRepositories(basePackages = "com.zjj")
@SpringBootApplication
@EnableGenEntityType
@EnableGenEntityInput
@EnableGenEntityQuery
@EnableGenEntityFuzzyQuery
public class ZeroDddTenantApplication implements CommandLineRunner {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {

        Map<String, UserDetails> map = Map.of(
                "root", SecurityUser.withUsername("root").password(passwordEncoder.encode("123456")).roles("admin").build(),
                "user", SecurityUser.withUsername("user").password(passwordEncoder.encode("123456")).roles("user").build()
        );

        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserDetails userDetails = map.get(username);
                if (userDetails == null) return userDetails;
                return SecurityUser.withUserDetails(userDetails).build();
            }
        };
    }

    @Autowired
    private RbacCacheManage rbacCacheManage;

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ZeroDddTenantApplication.class, args);
//        ApplicationModules modules = ApplicationModules.of(ZeroDddTenantApplication.class);
//        modules.forEach(System.out::println);
//        modules.verify();
//        new Documenter(modules)
//                .writeModulesAsPlantUml()
//                .writeIndividualModulesAsPlantUml();
        System.out.println(run);
    }

    @Override
    public void run(String... args) throws Exception {
        HttpResource httpResource = new HttpResource();

        httpResource.setUri("/tenant/v1/menu/resource/{id}");
        httpResource.setMethod(HttpMethod.DELETE.name());
        rbacCacheManage.putHttpResource(Map.of("delete:menu:resource", httpResource));

        GraphqlResource graphqlResource = new GraphqlResource();
        graphqlResource.setMethod(HttpMethod.POST.name());
        graphqlResource.setUri("/tenant/graphql");
        graphqlResource.setFunctionName("findTenant");
        graphqlResource.setType("query");

        GraphqlResource graphqlResource2 = new GraphqlResource();
        graphqlResource2.setMethod(HttpMethod.POST.name());
        graphqlResource2.setUri("/tenant/graphql");
        graphqlResource2.setFunctionName("run");
        graphqlResource2.setType("query");

        rbacCacheManage.putGraphqlResource(Map.of("query:run", graphqlResource2, "query:findTenant", graphqlResource));
        rbacCacheManage.putRole(Map.of("admin", Sets.newHashSet("delete:menu:resource", "query:run", "query:findTenant")));
        rbacCacheManage.putRole(Map.of("user", Sets.newHashSet("query:run", "delete:menu:resource")));

    }
}
