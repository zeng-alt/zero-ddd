package com.zjj.graphql.component.supper;

import com.zjj.graphql.component.utils.RepositoryUtils;
import graphql.schema.idl.RuntimeWiring;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.query.QuerydslDataFetcher;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月18日 21:17
 */
public record PageRuntimeWiringConfigurer(List<BaseRepository> baseRepositories) implements RuntimeWiringConfigurer {

    @Override
    public void configure(RuntimeWiring.Builder builder) {

        builder.type("Query", b -> {
            for (BaseRepository baseRepository : baseRepositories) {
                String type = RepositoryUtils.getGraphQlTypeName(baseRepository);
                b.dataFetcher("page" + type, QuerydslDataFetcher.builder(baseRepository).scrollable());
            }
            return b;
        });
    }
}
