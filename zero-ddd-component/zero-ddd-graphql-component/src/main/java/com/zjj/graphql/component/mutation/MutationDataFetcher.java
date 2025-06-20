package com.zjj.graphql.component.mutation;

import com.querydsl.core.types.EntityPath;
import com.zjj.domain.component.BaseRepository;
import com.zjj.graphql.component.utils.RepositoryUtils;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.querydsl.binding.EntityBuilder;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.util.TypeInformation;
import org.springframework.lang.Nullable;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.Validator;

import java.util.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月25日 11:22
 */
@Slf4j
public abstract class MutationDataFetcher<T, ID> {

    protected static final EntityBuilder ENTITY_BUILDER = new EntityBuilder(DefaultConversionService.getSharedInstance(), SimpleEntityPathResolver.INSTANCE);

    protected final TypeInformation<T> domainType;
    private final Class<ID> idType;

    protected final TransactionTemplate template;

    private final QuerydslBinderCustomizer<EntityPath<?>> customizer;

    public MutationDataFetcher(
            TypeInformation<T> domainType,
            Class<ID> idType,
            QuerydslBinderCustomizer<EntityPath<?>> customizer,
            TransactionTemplate template
    ) {
        this.domainType = domainType;
        this.idType = idType;
        this.customizer = customizer;
        this.template = template;
    }


    public static <T, ID, R> MutationBuilder<T, ID, R> builder(BaseRepository<T, ID> executor, TransactionTemplate template) {
        RepositoryMetadata repositoryMetadata = RepositoryUtils.getRepositoryMetadata(executor);
        return new MutationBuilder<>(executor, (Class<T>) repositoryMetadata.getDomainType(), (Class<ID>) repositoryMetadata.getIdType(), template);
    }


    protected T buildEntity(DataFetchingEnvironment environment) {
        QuerydslBindings bindings = new QuerydslBindings();
        EntityPath<?> path = SimpleEntityPathResolver.INSTANCE.createPath(this.domainType.getType());

        this.customizer.customize(bindings, path);
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap();
        this.addParameters((String)null, getArgumentValues(environment), parameters);
        MultiValueMap<String, Object> temp = new LinkedMultiValueMap<>();
        for (Map.Entry<String, List<Object>> entry : parameters.entrySet()) {
            String key = entry.getKey();
            if (key.contains(".")) {
                key = key.substring(key.indexOf(".") + 1);
            }
            temp.put(key, entry.getValue());
        }
        return ENTITY_BUILDER.getEntity(this.domainType, temp, bindings);
    }


    protected List<T> buildListEntity(DataFetchingEnvironment environment) {
        QuerydslBindings bindings = new QuerydslBindings();
        EntityPath<?> path = SimpleEntityPathResolver.INSTANCE.createPath(this.domainType.getType());

        this.customizer.customize(bindings, path);
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap();
        this.addParameters(null, getArgumentValues(environment), parameters);
        List<MultiValueMap<String, Object>> entity = new ArrayList<>();
        parameters.remove("ignoringNull");
        Map.Entry<String, List<Object>> next = parameters.entrySet().iterator().next();
        List<Object> value = next.getValue();
        for (Object o : value) {
            if (o instanceof Map<?, ?> map) {
                MultiValueMap<String, Object> temp = new LinkedMultiValueMap<>();
                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    String key = (String) entry.getKey();
                    if (key.contains(".")) {
                        key = key.substring(key.indexOf(".") + 1);
                    }
                    temp.put(key, entry.getValue() instanceof List ? (List<Object>) entry.getValue() : Collections.singletonList(entry.getValue()));
                }
                entity.add(temp);
            }
        }
        return entity.stream().map(e -> ENTITY_BUILDER.getEntity(this.domainType, e, bindings)).toList();
    }

    @SuppressWarnings("unchecked")
    private void addParameters(
            @Nullable String prefix, Map<String, Object> arguments, MultiValueMap<String, Object> parameters) {

        for (Map.Entry<String, Object> entry : arguments.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Map<?, ?> nested) {
                addParameters(((prefix != null) ? prefix + "." : "") + entry.getKey(), (Map<String, Object>) nested, parameters);
                continue;
            }
            List<Object> values = (value instanceof List) ? (List<Object>) value : Collections.singletonList(value);
            parameters.put(((prefix != null) ? prefix + "." : "") + entry.getKey(), values);
        }
    }

    /**
     * For a single argument that is a GraphQL input type, return the sub-map
     * under the argument name, or otherwise the top-level argument map.
     */
    @SuppressWarnings("unchecked")
    private static Map<String, Object> getArgumentValues(DataFetchingEnvironment environment) {
        Map<String, Object> arguments = environment.getArguments();
        if (environment.getFieldDefinition().getArguments().size() == 1) {
            String name = environment.getFieldDefinition().getArguments().get(0).getName();
            Object value = arguments.get(name);
            if (value instanceof Map<?, ?>) {
                return (Map<String, Object>) value;
            }
        }
        return arguments;
    }



    public String getDescription() {
        return "MutationDataFetcher<" + this.domainType.getType().getName() + ">";
    }
}
