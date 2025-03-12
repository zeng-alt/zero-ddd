package com.zjj.security.tenant.component.configuration;

import com.zjj.autoconfigure.component.cache.CacheType;
import com.zjj.security.tenant.component.EnableTenantJwtCache;
import com.zjj.security.tenant.component.supper.web.TenantJwtCacheManage;
import com.zjj.security.tenant.component.supper.web.TenantJwtL2CacheManage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月25日 11:32
 */
@Slf4j
public class TenantJwtCacheSelector implements ImportSelector {

    private static final boolean isJwtCachePresent = ClassUtils
            .isPresent("com.zjj.autoconfigure.component.security.jwt.JwtCacheManage", null);

    private static final boolean isRedisPresent = ClassUtils
            .isPresent("com.zjj.cache.component.config.RedisAutoConfiguration", null);

    private static final boolean isL2CachePresent = ClassUtils
            .isPresent("com.zjj.l2.cache.component.config.EnableL2Cache", null);

    @Override
    public String[] selectImports(AnnotationMetadata importMetadata) {
        if (!importMetadata.hasAnnotation(EnableTenantJwtCache.class.getName())
                && !importMetadata.hasMetaAnnotation(EnableTenantJwtCache.class.getName())) {
            return new String[0];
        }

        EnableTenantJwtCache annotation = importMetadata.getAnnotations().get(EnableTenantJwtCache.class).synthesize();
        List<String> imports = new ArrayList<>();

        if (!isJwtCachePresent || (!isRedisPresent && !isL2CachePresent)) {
            throw new IllegalArgumentException("""
                    请添加相关的依赖，
                    com.zjj.autoconfigure.component.security.jwt.JwtCacheManage
                    (com.zjj.cache.component.config.RedisAutoConfiguration或com.zjj.autoconfigure.component.l2cache.L2CacheManage)
                    """);
        }

        if (annotation.cache() == CacheType.REDIS && isRedisPresent) {
            imports.add(TenantJwtCacheManage.class.getName());
            log.info("Enable redis tenant jwt cache manager !!!");
        }
        if (annotation.cache() == CacheType.L2 && isL2CachePresent) {
            imports.add(TenantJwtL2CacheManage.class.getName());
            log.info("Enable l2 tenant jwt cache manager !!!");
        }
        return imports.toArray(new String[0]);
    }
}
