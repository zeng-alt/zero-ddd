package com.zjj.graphql.component.annotations;

import com.zjj.domain.component.BaseEntity;
import com.zjj.graphql.component.spi.EntitySaveHandler;

import java.lang.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月27日 09:41
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MutationEntity {

    Class<? extends EntitySaveHandler<? extends BaseEntity>>[] saveHandlers() default {};

    boolean enableSave() default true;

    boolean enableBatchSave() default true;

    boolean enableDelete() default true;

    boolean enableDeleteId() default true;
}
