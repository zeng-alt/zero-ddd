package com.zjj.graphql.component.query.sort;

import graphql.schema.DataFetchingEnvironment;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.query.SortStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月31日 21:35
 */
public class MultipleSortStrategy implements SortStrategy {

    /**
     * Return a {@link Sort} instance by extracting the sort information from
     * GraphQL arguments, or {@link Sort#unsorted()} otherwise.
     *
     * @param environment the data fetching environment
     */
    @Override
    public Sort extract(DataFetchingEnvironment environment) {
        return Sort.by(getSort(environment));
    }

    protected List<Sort.Order> getSort(DataFetchingEnvironment env) {
        Object sort = env.getArgument("sort");
        List<Sort.Order> list = new ArrayList<>();
        if (sort instanceof Collection<?> orders) {

            for (Object order : orders) {
                if (order instanceof LinkedHashMap) {
                    LinkedHashMap<String, Object> orderMap = (LinkedHashMap<String, Object>) order;

                    if (!orderMap.containsKey("property")) {
                        throw new IllegalArgumentException();
                    }

                    list.add(new Sort.Order(
                            Sort.Direction.fromString(orderMap.get("direction").toString()),
                            orderMap.get("property").toString(),
                            (Boolean) orderMap.get("ignoreCase"),
                            Sort.NullHandling.valueOf(orderMap.get("nullHandling").toString()))
                    );
                }
            }
        }

        return list;
    }
}
