package com.zjj.tenant.datasource.component;

import com.zjj.autoconfigure.component.tenant.TenantMode;
import com.zjj.tenant.database.component.TenantDatabaseAutoConfiguration;
import com.zjj.tenant.management.component.config.DataSourceConfiguration;
import com.zjj.tenant.management.component.config.LiquibaseConfiguration;
import com.zjj.tenant.management.component.config.TenantManagementAutoConfiguration;
import com.zjj.tenant.mix.component.TenantMixedAutoConfiguration;
import com.zjj.tenant.mix.component.TenantMixedRoutingDatasource;
import com.zjj.tenant.schema.component.TenantSchemaAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:56
 */
@Slf4j
final class TenantSelector implements ImportSelector {

    @Override
    public @NonNull String[] selectImports(AnnotationMetadata importMetadata) {
        if (!importMetadata.hasAnnotation(EnableMultiTenancy.class.getName())
                && !importMetadata.hasMetaAnnotation(EnableMultiTenancy.class.getName())) {
            return new String[0];
        }

        EnableMultiTenancy annotation = importMetadata.getAnnotations().get(EnableMultiTenancy.class).synthesize();
        List<String> imports = new ArrayList<>();

        imports.add(SwitchTenantMethodInterceptor.class.getName());

        if (annotation.mode() == TenantMode.DATABASE) {
            imports.add(DataSourceConfiguration.class.getName());
            imports.add(TenantDatabaseAutoConfiguration.class.getName());
            if (annotation.dynamicLiquibase()) {
                imports.add(TenantManagementAutoConfiguration.class.getName());
            }
            if (annotation.enabledLiquibase()) {
                imports.add(LiquibaseConfiguration.class.getName());
            }
            log.info("Enable database multi-tenancy mode!!!");
        }
        if ((annotation.mode() == TenantMode.SCHEMA)) {
            imports.add(DataSourceConfiguration.class.getName());
            imports.add(TenantSchemaAutoConfiguration.class.getName());
            if (annotation.dynamicLiquibase()) {
                imports.add(TenantManagementAutoConfiguration.class.getName());
            }
            if (annotation.enabledLiquibase()) {
                imports.add(LiquibaseConfiguration.class.getName());
            }
            log.info("Enable schema multi-tenancy mode!!!");
        }

        if ((annotation.mode() == TenantMode.MIXED)) {
            imports.add(DataSourceConfiguration.class.getName());
            imports.add(TenantMixedAutoConfiguration.class.getName());
            if (annotation.dynamicLiquibase()) {
                imports.add(TenantManagementAutoConfiguration.class.getName());
            }
            if (annotation.enabledLiquibase()) {
                imports.add(LiquibaseConfiguration.class.getName());
            }
            log.info("Enable mixed multi-tenancy mode!!!");
        }


        if (annotation.mode() == TenantMode.COLUMN) {
            log.info("Enable column multi-tenancy mode!!!");
        }

        return imports.toArray(new String[0]);
    }

}
