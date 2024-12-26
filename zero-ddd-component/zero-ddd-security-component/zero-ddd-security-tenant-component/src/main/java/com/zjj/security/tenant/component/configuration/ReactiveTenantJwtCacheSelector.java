package com.zjj.security.tenant.component.configuration;

import com.zjj.autoconfigure.component.cache.CacheType;
import com.zjj.security.tenant.component.EnableReactiveTenantJwtCache;
import com.zjj.security.tenant.component.EnableTenantJwtCache;
import com.zjj.security.tenant.component.supper.ReactiveTenantJwtCacheManage;
import com.zjj.security.tenant.component.supper.TenantJwtCacheManage;
import com.zjj.security.tenant.component.supper.TenantJwtL2CacheManage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月26日 21:17
 */
@Slf4j
public class ReactiveTenantJwtCacheSelector implements ImportSelector {

    private static final boolean isJwtCachePresent = ClassUtils
            .isPresent("com.zjj.autoconfigure.component.security.jwt.ReactiveJwtCacheManage", null);

    private static final boolean isRedisPresent = ClassUtils
            .isPresent("com.zjj.cache.component.config.RedisAutoConfiguration", null);

    private static final boolean isL2CachePresent = ClassUtils
            .isPresent("com.zjj.l2.cache.component.config.EnableL2Cache", null);

    @Override
    public String[] selectImports(AnnotationMetadata importMetadata) {
        if (!importMetadata.hasAnnotation(EnableReactiveTenantJwtCache.class.getName())
                && !importMetadata.hasMetaAnnotation(EnableReactiveTenantJwtCache.class.getName())) {
            return new String[0];
        }

        EnableReactiveTenantJwtCache annotation = importMetadata.getAnnotations().get(EnableReactiveTenantJwtCache.class).synthesize();
        List<String> imports = new ArrayList<>();

        if (!isJwtCachePresent || (!isRedisPresent && !isL2CachePresent)) {
            throw new IllegalArgumentException("""
                    请添加相关的依赖，
                    com.zjj.autoconfigure.component.security.jwt.ReactiveJwtCacheManage
                    (com.zjj.cache.component.config.RedisAutoConfiguration或com.zjj.autoconfigure.component.l2cache.L2CacheManage)
                    """);
        }

        if (annotation.cache() == CacheType.REDIS && isRedisPresent) {
            imports.add(ReactiveTenantJwtCacheManage.class.getName());
            log.info("Enable redis tenant jwt cache manager !!!");
        }
//        if (annotation.cache() == CacheType.L2 && isL2CachePresent) {
//            imports.add(TenantJwtL2CacheManage.class.getName());
//            log.info("Enable l2 tenant jwt cache manager !!!");
//        }
        return imports.toArray(new String[0]);
    }
}
