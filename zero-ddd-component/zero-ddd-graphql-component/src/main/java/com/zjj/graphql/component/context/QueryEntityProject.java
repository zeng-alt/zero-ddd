package com.zjj.graphql.component.context;

import lombok.Data;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月09日 15:01
 */
@Data
public class QueryEntityProject {

    private Class<?> fuzzyQueryProject;
    private Class<?> fuzzyFindProject;
    private Class<?> fuzzyPageProject;
    private Class<?> conditionQueryProject;
    private Class<?> conditionFindProject;
    private Class<?> conditionPageProject;
    private Class<?> queryProject;
    private Class<?> findProject;
    private Class<?> pageProject;
}
