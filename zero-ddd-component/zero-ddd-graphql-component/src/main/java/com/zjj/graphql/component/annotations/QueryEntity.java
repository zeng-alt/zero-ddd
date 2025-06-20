package com.zjj.graphql.component.annotations;


import java.lang.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月09日 14:45
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryEntity {

    Class<?> fuzzyQueryProject() default Void.class;

    Class<?> fuzzyFindProject() default Void.class;

    Class<?> fuzzyPageProject() default Void.class;
    Class<?> conditionQueryProject() default Void.class;
    Class<?> conditionFindProject() default Void.class;
    
    Class<?> conditionPageProject() default Void.class;

    Class<?> queryProject() default Void.class;

    Class<?> findProject() default Void.class;

    Class<?> pageProject() default Void.class;
}
