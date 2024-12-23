package com.zjj.tenant.datasource.component;

import com.zjj.autoconfigure.component.cache.CacheType;
import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.security.tenant.component.configuration.SecurityReactiveTenantAutoConfiguration;
import com.zjj.security.tenant.component.configuration.SecurityTenantAutoConfiguration;
import com.zjj.security.tenant.component.supper.TenantJwtCacheManage;
import com.zjj.security.tenant.component.supper.TenantJwtL2CacheManage;
import com.zjj.tenant.column.component.TenantColumnAutoConfiguration;
import com.zjj.tenant.database.component.TenantDatabaseAutoConfiguration;
import com.zjj.tenant.management.component.config.TenantManagementAutoConfiguration;
import com.zjj.tenant.schema.component.TenantSchemaAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:56
 */
@Slf4j
final class TenantSelector implements ImportSelector {


    private static final boolean isJwtCachePresent = ClassUtils
            .isPresent("com.zjj.autoconfigure.component.security.jwt.JwtCacheManage", null);

    private static final boolean isRedisPresent = ClassUtils
            .isPresent("com.zjj.cache.component.config.RedisAutoConfiguration", null);

    private static final boolean isL2CachePresent = ClassUtils
            .isPresent("com.zjj.l2.cache.component.config.EnableL2Cache", null);
    @Override
    public @NonNull String[] selectImports(AnnotationMetadata importMetadata) {
        if (!importMetadata.hasAnnotation(EnableMultiTenancy.class.getName())
                && !importMetadata.hasMetaAnnotation(EnableMultiTenancy.class.getName())) {
            return new String[0];
        }

        EnableMultiTenancy annotation = importMetadata.getAnnotations().get(EnableMultiTenancy.class).synthesize();
        List<String> imports = new ArrayList<>(getCacheImports(annotation.cache()));


        imports.add(TenantColumnAutoConfiguration.class.getName());
        imports.addAll(getTenantFilterImports(annotation.type()));

        if (annotation.mode() == TenantMode.DATABASE) {
            imports.add(TenantManagementAutoConfiguration.class.getName());
            imports.add(TenantDatabaseAutoConfiguration.class.getName());
            log.info("Enable database multi-tenancy mode!!!");
        }
        if ((annotation.mode() == TenantMode.SCHEMA)) {
            imports.add(TenantManagementAutoConfiguration.class.getName());
            imports.add(TenantSchemaAutoConfiguration.class.getName());
            log.info("Enable schema multi-tenancy mode!!!");
        }
        if (annotation.mode() == TenantMode.COLUMN) {
            log.info("Enable column multi-tenancy mode!!!");
        }

        return imports.toArray(new String[0]);
    }


    private List<String> getTenantFilterImports(ConditionalOnWebApplication.Type type) {
        List<String> imports = new ArrayList<>();
        if (type == ConditionalOnWebApplication.Type.REACTIVE) {
            imports.add(SecurityReactiveTenantAutoConfiguration.class.getName());
        }

        if (type == ConditionalOnWebApplication.Type.SERVLET) {
            imports.add(SecurityTenantAutoConfiguration.class.getName());
        }

        return imports;
    }


    private List<String> getCacheImports(CacheType cacheType) {
        List<String> imports = new ArrayList<>();

        if (!isJwtCachePresent || (!isRedisPresent && !isL2CachePresent)) {
            throw new IllegalArgumentException("""
                    请添加相关的依赖，
                    com.zjj.autoconfigure.component.security.jwt.JwtCacheManage
                    (com.zjj.cache.component.config.RedisAutoConfiguration或com.zjj.autoconfigure.component.l2cache.L2CacheManage)
                    """);
        }

        if (cacheType == CacheType.REDIS && isRedisPresent) {
            imports.add(TenantJwtCacheManage.class.getName());
        }
        if (cacheType == CacheType.L2 && isL2CachePresent) {
            imports.add(TenantJwtL2CacheManage.class.getName());
        }
        return imports;
    }
}
