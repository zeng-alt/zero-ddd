package com.zjj.graphql.component.query.fuzzy;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.zjj.graphql.component.registration.AutoRegistrationRuntimeWiringConfigurer;
import com.zjj.graphql.component.registration.PropertySelection;
import com.zjj.graphql.component.utils.RepositoryUtils;
import graphql.schema.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.QuerydslPredicateBuilder;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.data.util.TypeInformation;
import org.springframework.graphql.data.GraphQlRepository;
import org.springframework.graphql.data.pagination.CursorStrategy;
import org.springframework.graphql.data.query.*;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.graphql.execution.SelfDescribingDataFetcher;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.function.Function;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月19日 23:29
 */
public abstract class QuerydslFuzzyDataFetcher<T> {
    private static final Log logger = LogFactory.getLog(QueryByExampleDataFetcher.class);

    private static final QuerydslPredicateBuilder BUILDER = new QuerydslPredicateBuilder(new QuerydslFuzzyBinding(),
            DefaultConversionService.getSharedInstance(), SimpleEntityPathResolver.INSTANCE);

    @SuppressWarnings("rawtypes")
    private static final QuerydslBinderCustomizer NO_OP_BINDER_CUSTOMIZER = (bindings, root) -> {
    };


    private final TypeInformation<T> domainType;

    private final QuerydslBinderCustomizer<EntityPath<?>> customizer;


    public QuerydslFuzzyDataFetcher(TypeInformation<T> domainType, QuerydslBinderCustomizer<EntityPath<?>> customizer) {
        this.domainType = domainType;
        this.customizer = customizer;
    }


    /**
     * Provides shared implementation of
     * {@link SelfDescribingDataFetcher#getDescription()} for all subclasses.
     * @since 1.2.0
     */
    public String getDescription() {
        return "QuerydslDataFetcher<" + this.domainType.getType().getName() + ">";
    }

    /**
     * Prepare a {@link Predicate} from GraphQL request arguments, also applying
     * any {@link QuerydslBinderCustomizer} that may have been configured.
     * @param environment contextual info for the GraphQL request
     * @return the resulting predicate
     */
    @SuppressWarnings({"unchecked"})
    protected Predicate buildPredicate(DataFetchingEnvironment environment) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        QuerydslBindings bindings = new QuerydslBindings();
        EntityPath<?> path = SimpleEntityPathResolver.INSTANCE.createPath(this.domainType.getType());

        this.customizer.customize(bindings, path);

        for (Map.Entry<String, Object> entry : getArgumentValues(environment).entrySet()) {
            Object value = entry.getValue();
            List<Object> values = (value instanceof List) ? (List<Object>) value : Collections.singletonList(value);
            parameters.put(entry.getKey(), values);
        }

        return BUILDER.getPredicate(this.domainType, parameters, bindings);
    }

    /**
     * For a single argument that is a GraphQL input type, return the sub-map
     * under the argument name, or otherwise the top-level argument map.
     */
    @SuppressWarnings("unchecked")
    private static Map<String, Object> getArgumentValues(DataFetchingEnvironment environment) {
        Map<String, Object> arguments = environment.getArguments();

        for (GraphQLArgument argument : environment.getFieldDefinition().getArguments()) {
            if (argument.getType() instanceof  GraphQLInputObjectType type) {
                if (type.getName().equals("Sort") || type.getName().equals("PageQuery")) {
                    continue;
                }
                String name = argument.getName();
                Object value = arguments.get(name);
                if (value instanceof Map<?, ?>) {
                    return (Map<String, Object>) value;
                }
            }
        }

        return arguments;
    }

    protected boolean requiresProjection(Class<?> resultType) {
        return !resultType.equals(this.domainType.getType());
    }

    protected Collection<String> buildPropertyPaths(DataFetchingFieldSelectionSet selection, Class<?> resultType) {

        // Compute selection only for non-projections
        if (this.domainType.getType().equals(resultType) ||
                this.domainType.getType().isAssignableFrom(resultType) ||
                this.domainType.isSubTypeOf(resultType)) {
            return PropertySelection.create(this.domainType, selection).toList();
        }
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return getDescription();
    }


    /**
     * Create a new {@link QuerydslDataFetcher.Builder} accepting {@link QuerydslPredicateExecutor}
     * to build a {@link DataFetcher}.
     * @param executor the repository object to use
     * @param <T> result type
     * @return a new builder
     */
    public static <T> FuzzyBuilder<T, T> builder(QuerydslPredicateExecutor<T> executor) {
        return new FuzzyBuilder<>(executor, RepositoryUtils.getDomainType(executor));
    }

    /**
     * Create a new {@link QuerydslDataFetcher.ReactiveBuilder} accepting
     * {@link ReactiveQuerydslPredicateExecutor} to build a reactive {@link DataFetcher}.
     * @param executor the repository object to use
     * @param <T> result type
     * @return a new builder
     */
    public static <T> ReactiveFuzzyBuilder<T, T> builder(ReactiveQuerydslPredicateExecutor<T> executor) {
        return new ReactiveFuzzyBuilder<>(executor, RepositoryUtils.getDomainType(executor));
    }

    /**
     * Variation of {@link #autoRegistrationConfigurer(List, List, CursorStrategy, ScrollSubrange)}
     * without a {@code CursorStrategy} and default {@link ScrollSubrange}.
     * For default values, see the respective methods on {@link QuerydslDataFetcher.Builder} and
     * {@link QuerydslDataFetcher.ReactiveBuilder}.
     * @param executors repositories to consider for registration
     * @param reactiveExecutors reactive repositories to consider for registration
     */
    public static RuntimeWiringConfigurer autoRegistrationConfigurer(
            List<QuerydslPredicateExecutor<?>> executors,
            List<ReactiveQuerydslPredicateExecutor<?>> reactiveExecutors) {

        return autoRegistrationConfigurer(executors, reactiveExecutors, null, null);
    }

    /**
     * Return a {@link RuntimeWiringConfigurer} that installs a
     * {@link graphql.schema.idl.WiringFactory} to find queries with a return
     * type whose name matches to the domain type name of the given repositories
     * and registers {@link DataFetcher}s for them.
     *
     * <p><strong>Note:</strong> This applies only to top-level queries and
     * repositories annotated with {@link GraphQlRepository @GraphQlRepository}.
     * If a repository is also an instance of {@link QuerydslBinderCustomizer},
     * this is transparently detected and applied through the
     * {@code QuerydslDataFetcher} builder  methods.
     * @param executors repositories to consider for registration
     * @param reactiveExecutors reactive repositories to consider for registration
     * @param cursorStrategy for decoding cursors in pagination requests;
     * if {@code null}, then {@link FuzzyBuilder#cursorStrategy} defaults apply.
     * @param defaultScrollSubrange default parameters for scrolling;
     * if {@code null}, then {@link FuzzyBuilder#defaultScrollSubrange} defaults apply.
     * @return the created configurer
     * @since 1.2.0
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static RuntimeWiringConfigurer autoRegistrationConfigurer(
            List<QuerydslPredicateExecutor<?>> executors,
            List<ReactiveQuerydslPredicateExecutor<?>> reactiveExecutors,
            @Nullable CursorStrategy<ScrollPosition> cursorStrategy,
            @Nullable ScrollSubrange defaultScrollSubrange) {

        Map<String, AutoRegistrationRuntimeWiringConfigurer.DataFetcherFactory> factories = new HashMap<>();

        for (QuerydslPredicateExecutor<?> executor : executors) {
            String typeName = RepositoryUtils.getGraphQlTypeName(executor);
            if (typeName != null) {
                FuzzyBuilder builder = customize(executor,
                        QuerydslFuzzyDataFetcher.builder(executor)
                                .cursorStrategy(cursorStrategy)
                                .defaultScrollSubrange(defaultScrollSubrange)
                                .customizer(customizer(executor)));

                factories.put(typeName, new AutoRegistrationRuntimeWiringConfigurer.DataFetcherFactory() {
                    @Override
                    public DataFetcher<?> single() {
                        return builder.single();
                    }

                    @Override
                    public DataFetcher<?> many() {
                        return builder.many();
                    }

                    @Override
                    public DataFetcher<?> scrollable() {
                        return builder.scrollable();
                    }
                });
            }
        }

        for (ReactiveQuerydslPredicateExecutor<?> executor : reactiveExecutors) {
            String typeName = RepositoryUtils.getGraphQlTypeName(executor);
            if (typeName != null) {
                ReactiveFuzzyBuilder builder = customize(executor,
                        QuerydslFuzzyDataFetcher.builder(executor)
                                .cursorStrategy(cursorStrategy)
                                .defaultScrollSubrange(defaultScrollSubrange)
                                .customizer(customizer(executor)));

                factories.put(typeName, new AutoRegistrationRuntimeWiringConfigurer.DataFetcherFactory() {
                    @Override
                    public DataFetcher<?> single() {
                        return builder.single();
                    }

                    @Override
                    public DataFetcher<?> many() {
                        return builder.many();
                    }

                    @Override
                    public DataFetcher<?> scrollable() {
                        return builder.scrollable();
                    }
                });
            }
        }

        if (logger.isTraceEnabled()) {
            logger.trace("Auto-registration candidate typeNames " + factories.keySet());
        }

        return new AutoRegistrationRuntimeWiringConfigurer(factories);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static FuzzyBuilder customize(QuerydslPredicateExecutor<?> executor, FuzzyBuilder builder) {
        if (executor instanceof QuerydslFuzzyDataFetcher.QuerydslBuilderCustomizer<?> customizer) {
            return customizer.customize(builder);
        }
        return builder;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static ReactiveFuzzyBuilder customize(ReactiveQuerydslPredicateExecutor<?> executor, ReactiveFuzzyBuilder builder) {
        if (executor instanceof QuerydslFuzzyDataFetcher.ReactiveQuerydslBuilderCustomizer<?> customizer) {
            return customizer.customize(builder);
        }
        return builder;
    }

    @SuppressWarnings("rawtypes")
    private static QuerydslBinderCustomizer customizer(Object executor) {
        return (executor instanceof QuerydslBinderCustomizer<?>) ?
                (QuerydslBinderCustomizer<? extends EntityPath<?>>) executor :
                NO_OP_BINDER_CUSTOMIZER;
    }


    /**
     * Callback interface that can be used to customize QuerydslDataFetcher
     * {@link QuerydslDataFetcher.Builder} to change its configuration.
     * <p>This is supported by {@link #autoRegistrationConfigurer(List, List)
     * Auto-registration}, which detects if a repository implements this
     * interface and applies it accordingly.
     *
     * @param <T> the domain type
     * @since 1.1.1
     */
    public interface QuerydslBuilderCustomizer<T> {

        /**
         * Callback to customize a {@link QuerydslDataFetcher.Builder} instance.
         * @param builder builder to customize
         */
        FuzzyBuilder<T, ?> customize(FuzzyBuilder<T, ?> builder);

    }




    /**
     * Callback interface that can be used to customize QuerydslDataFetcher
     * {@link QuerydslDataFetcher.ReactiveBuilder} to change its configuration.
     * <p>This is supported by {@link #autoRegistrationConfigurer(List, List)
     * Auto-registration}, which detects if a repository implements this
     * interface and applies it accordingly.
     * @param <T> the domain type
     * @since 1.1.1
     */
    public interface ReactiveQuerydslBuilderCustomizer<T> {

        /**
         * Callback to customize a {@link QuerydslDataFetcher.ReactiveBuilder} instance.
         * @param builder builder to customize
         */
        ReactiveFuzzyBuilder<T, ?> customize(ReactiveFuzzyBuilder<T, ?> builder);

    }


    static class SingleEntityFetcher<T, R>
            extends QuerydslFuzzyDataFetcher<T> implements SelfDescribingDataFetcher<R> {

        private final QuerydslPredicateExecutor<T> executor;

        private final Class<R> resultType;

        private final Sort sort;

        @SuppressWarnings({"unchecked", "rawtypes"})
        public SingleEntityFetcher(
                QuerydslPredicateExecutor<T> executor, TypeInformation<T> domainType, Class<R> resultType,
                Sort sort, QuerydslBinderCustomizer<? extends EntityPath<T>> customizer) {

            super(domainType, (QuerydslBinderCustomizer) customizer);
            this.executor = executor;
            this.resultType = resultType;
            this.sort = sort;
        }

        @Override
        public ResolvableType getReturnType() {
            return ResolvableType.forClass(this.resultType);
        }

        @Override
        @SuppressWarnings({"ConstantConditions", "unchecked"})
        public R get(DataFetchingEnvironment env) {
            return this.executor.findBy(buildPredicate(env), (query) -> {
                FluentQuery.FetchableFluentQuery<R> queryToUse = (FluentQuery.FetchableFluentQuery<R>) query;

                if (this.sort.isSorted()) {
                    queryToUse = queryToUse.sortBy(this.sort);
                }

                Class<R> resultType = this.resultType;
                if (requiresProjection(resultType)) {
                    queryToUse = queryToUse.as(resultType);
                }
                else {
                    queryToUse = queryToUse.project(buildPropertyPaths(env.getSelectionSet(), resultType));
                }
//                queryToUse.
                return queryToUse.first();
            }).orElse(null);
        }

    }


    static class ManyEntityFetcher<T, R>
            extends QuerydslFuzzyDataFetcher<T> implements SelfDescribingDataFetcher<Iterable<R>> {

        private final QuerydslPredicateExecutor<T> executor;

        private final Class<R> resultType;


        @SuppressWarnings({"unchecked", "rawtypes"})
        ManyEntityFetcher(
                QuerydslPredicateExecutor<T> executor, TypeInformation<T> domainType, Class<R> resultType,
                QuerydslBinderCustomizer<? extends EntityPath<T>> customizer) {

            super(domainType, (QuerydslBinderCustomizer) customizer);
            this.executor = executor;
            this.resultType = resultType;
        }

        @Override
        public ResolvableType getReturnType() {
            return ResolvableType.forClassWithGenerics(Iterable.class, this.resultType);
        }

        @Override
        @SuppressWarnings("unchecked")
        public Iterable<R> get(DataFetchingEnvironment env) {
            return this.executor.findBy(buildPredicate(env), (query) -> {
                FluentQuery.FetchableFluentQuery<R> queryToUse = (FluentQuery.FetchableFluentQuery<R>) query;

                Sort sort = RepositoryUtils.getSort(env);
                if (sort != null && sort.isSorted()) {
                    queryToUse = queryToUse.sortBy(sort);
                }


                if (requiresProjection(this.resultType)) {
                    queryToUse = queryToUse.as(this.resultType);
                }
                else {
                    queryToUse = queryToUse.project(buildPropertyPaths(env.getSelectionSet(), this.resultType));
                }

                return getResult(queryToUse, env);
            });
        }

        protected Iterable<R> getResult(FluentQuery.FetchableFluentQuery<R> queryToUse, DataFetchingEnvironment env) {
            return queryToUse.all();
        }

    }


    static class ScrollableEntityFetcher<T, R> extends ManyEntityFetcher<T, R> {

        private final CursorStrategy<ScrollPosition> cursorStrategy;

        private final int defaultCount;

        private final Function<Boolean, ScrollPosition> defaultPosition;

        ScrollableEntityFetcher(QuerydslPredicateExecutor<T> executor,
                                TypeInformation<T> domainType,
                                Class<R> resultType,
                                CursorStrategy<ScrollPosition> cursorStrategy,
                                int defaultCount,
                                Function<Boolean, ScrollPosition> defaultPosition,
                                QuerydslBinderCustomizer<? extends EntityPath<T>> customizer) {

            super(executor, domainType, resultType, customizer);

            Assert.notNull(cursorStrategy, "CursorStrategy is required");
            Assert.notNull(defaultPosition, "'defaultPosition' is required");

            this.cursorStrategy = cursorStrategy;
            this.defaultCount = defaultCount;
            this.defaultPosition = defaultPosition;
        }

        @Override
        protected Iterable<R> getResult(FluentQuery.FetchableFluentQuery<R> queryToUse, DataFetchingEnvironment env) {
            ScrollSubrange range = RepositoryUtils.getPageScrollSubrange(env, this.cursorStrategy);
            int count = range.count().orElse(this.defaultCount);
            ScrollPosition position = (range.position().isPresent() ?
                    range.position().get() : this.defaultPosition.apply(range.forward()));
            return queryToUse.limit(count).scroll(position);
        }

    }


    private static class ReactiveSingleEntityFetcher<T, R>
            extends QuerydslFuzzyDataFetcher<T> implements SelfDescribingDataFetcher<Mono<R>> {

        private final ReactiveQuerydslPredicateExecutor<T> executor;

        private final Class<R> resultType;

        private final Sort sort;

        @SuppressWarnings({"unchecked", "rawtypes"})
        ReactiveSingleEntityFetcher(
                ReactiveQuerydslPredicateExecutor<T> executor, TypeInformation<T> domainType, Class<R> resultType,
                Sort sort, QuerydslBinderCustomizer<? extends EntityPath<T>> customizer) {

            super(domainType, (QuerydslBinderCustomizer) customizer);

            this.executor = executor;
            this.resultType = resultType;
            this.sort = sort;
        }

        @Override
        public ResolvableType getReturnType() {
            return ResolvableType.forClassWithGenerics(Mono.class, this.resultType);
        }

        @Override
        @SuppressWarnings("unchecked")
        public Mono<R> get(DataFetchingEnvironment env) {
            return this.executor.findBy(buildPredicate(env), (query) -> {
                FluentQuery.ReactiveFluentQuery<R> queryToUse = (FluentQuery.ReactiveFluentQuery<R>) query;

                if (this.sort.isSorted()) {
                    queryToUse = queryToUse.sortBy(this.sort);
                }

                if (requiresProjection(this.resultType)) {
                    queryToUse = queryToUse.as(this.resultType);
                }
                else {
                    queryToUse = queryToUse.project(buildPropertyPaths(env.getSelectionSet(), this.resultType));
                }

                return queryToUse.first();
            });
        }

    }


    private static class ReactiveManyEntityFetcher<T, R>
            extends QuerydslFuzzyDataFetcher<T> implements SelfDescribingDataFetcher<Flux<R>> {

        private final ReactiveQuerydslPredicateExecutor<T> executor;

        private final Class<R> resultType;

        private final Sort sort;

        @SuppressWarnings({"unchecked", "rawtypes"})
        ReactiveManyEntityFetcher(
                ReactiveQuerydslPredicateExecutor<T> executor, TypeInformation<T> domainType, Class<R> resultType,
                Sort sort, QuerydslBinderCustomizer<? extends EntityPath<T>> customizer) {

            super(domainType, (QuerydslBinderCustomizer) customizer);

            this.executor = executor;
            this.resultType = resultType;
            this.sort = sort;
        }

        @Override
        public ResolvableType getReturnType() {
            return ResolvableType.forClassWithGenerics(Flux.class, this.resultType);
        }

        @Override
        @SuppressWarnings("unchecked")
        public Flux<R> get(DataFetchingEnvironment env) {
            return this.executor.findBy(buildPredicate(env), (query) -> {
                FluentQuery.ReactiveFluentQuery<R> queryToUse = (FluentQuery.ReactiveFluentQuery<R>) query;

                if (this.sort.isSorted()) {
                    queryToUse = queryToUse.sortBy(this.sort);
                }

                if (requiresProjection(this.resultType)) {
                    queryToUse = queryToUse.as(this.resultType);
                }
                else {
                    queryToUse = queryToUse.project(buildPropertyPaths(env.getSelectionSet(), this.resultType));
                }

                return queryToUse.all();
            });
        }

    }


    private static class ReactiveScrollableEntityFetcher<T, R>
            extends QuerydslFuzzyDataFetcher<T> implements SelfDescribingDataFetcher<Mono<Iterable<R>>> {

        private final ReactiveQuerydslPredicateExecutor<T> executor;

        private final Class<R> resultType;

        private final ResolvableType scrollableResultType;

        private final CursorStrategy<ScrollPosition> cursorStrategy;

        private final int defaultCount;

        private final Function<Boolean, ScrollPosition> defaultPosition;

        private final Sort sort;

        @SuppressWarnings({"unchecked", "rawtypes"})
        ReactiveScrollableEntityFetcher(ReactiveQuerydslPredicateExecutor<T> executor,
                                        TypeInformation<T> domainType,
                                        Class<R> resultType,
                                        CursorStrategy<ScrollPosition> cursorStrategy,
                                        int defaultCount,
                                        Function<Boolean, ScrollPosition> defaultPosition,
                                        Sort sort,
                                        QuerydslBinderCustomizer<? extends EntityPath<T>> customizer) {

            super(domainType, (QuerydslBinderCustomizer) customizer);

            Assert.notNull(cursorStrategy, "CursorStrategy is required");
            Assert.notNull(defaultPosition, "'defaultPosition' is required");

            this.executor = executor;
            this.resultType = resultType;
            this.scrollableResultType = ResolvableType.forClassWithGenerics(Iterable.class, resultType);
            this.cursorStrategy = cursorStrategy;
            this.defaultCount = defaultCount;
            this.defaultPosition = defaultPosition;
            this.sort = sort;
        }

        @Override
        public ResolvableType getReturnType() {
            return ResolvableType.forClassWithGenerics(Mono.class, this.scrollableResultType);
        }

        @Override
        @SuppressWarnings("unchecked")
        public Mono<Iterable<R>> get(DataFetchingEnvironment env) {
            return this.executor.findBy(buildPredicate(env), (query) -> {
                FluentQuery.ReactiveFluentQuery<R> queryToUse = (FluentQuery.ReactiveFluentQuery<R>) query;

                if (this.sort.isSorted()) {
                    queryToUse = queryToUse.sortBy(this.sort);
                }

                if (requiresProjection(this.resultType)) {
                    queryToUse = queryToUse.as(this.resultType);
                }
                else {
                    queryToUse = queryToUse.project(buildPropertyPaths(env.getSelectionSet(), this.resultType));
                }

                ScrollSubrange range = RepositoryUtils.getPageScrollSubrange(env, this.cursorStrategy);
                int count = range.count().orElse(this.defaultCount);
                ScrollPosition position = (range.position().isPresent() ?
                        range.position().get() : this.defaultPosition.apply(range.forward()));
                return queryToUse.limit(count).scroll(position).map(Function.identity());
            });
        }

    }
}
