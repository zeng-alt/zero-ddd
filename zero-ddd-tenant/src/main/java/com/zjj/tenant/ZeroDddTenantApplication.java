package com.zjj.tenant;



import com.zjj.graphql.component.config.EnableGenEntityFuzzyQuery;
import com.zjj.graphql.component.config.EnableGenEntityInput;
import com.zjj.graphql.component.config.EnableGenEntityQuery;
import com.zjj.graphql.component.config.EnableGenEntityType;


import org.jmolecules.bytebuddy.JMoleculesSpringDataPlugin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.modulith.ApplicationModule;
import org.springframework.modulith.Modulith;
import org.springframework.modulith.Modulithic;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月29日 21:34
 */
@EnableJpaRepositories(basePackages = "com.zjj")
@SpringBootApplication
@EnableGenEntityType
@EnableGenEntityInput
@EnableGenEntityQuery
@EnableGenEntityFuzzyQuery
public class ZeroDddTenantApplication {

    public static void main(String[] args) {
//        ConfigurableApplicationContext run = SpringApplication.run(ZeroDddTenantApplication.class, args);
        ApplicationModules modules = ApplicationModules.of(ZeroDddTenantApplication.class);
        modules.forEach(System.out::println);
        modules.verify();
//        new Documenter(modules)
//                .writeModulesAsPlantUml()
//                .writeIndividualModulesAsPlantUml();
//        System.out.println(run);
    }
}
