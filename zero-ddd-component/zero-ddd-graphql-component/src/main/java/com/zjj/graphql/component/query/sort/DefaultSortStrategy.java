package com.zjj.graphql.component.query.sort;

import graphql.com.google.common.collect.Lists;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.query.AbstractSortStrategy;

import java.util.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月31日 10:24
 */
public class DefaultSortStrategy extends AbstractSortStrategy {
    /**
     * Return the sort properties to use, or an empty list if there are none.
     *
     * @param environment the data fetching environment for this operation
     */
    @Override
    protected List<String> getProperties(DataFetchingEnvironment environment) {
        Object sort = environment.getArgument("sort");
        List<String> list = Lists.newArrayList();

        if (sort == null) {
            return list;
        }

        if (sort instanceof Collection<?> sortList) {
            for (Object o : sortList) {

            }
        }


        return list;
    }

    /**
     * Return the sort direction to use, or {@code null}.
     *
     * @param environment the data fetching environment for this operation
     */
    @Override
    protected Sort.Direction getDirection(DataFetchingEnvironment environment) {
        return null;
    }
}
