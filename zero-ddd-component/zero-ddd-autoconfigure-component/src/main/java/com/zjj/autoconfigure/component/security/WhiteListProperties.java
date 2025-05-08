package com.zjj.autoconfigure.component.security;

import com.zjj.autoconfigure.component.security.rbac.GraphqlResource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月28日 21:42
 */
@Data
@Component
@ConfigurationProperties(prefix = "security.filter")
public class WhiteListProperties {

    private Set<String> ignoreUrl = new HashSet<>();
    private Map<String, WhiteList> graphql = new HashMap<>();

    @Data
    public static class WhiteList {
        private Set<String> query = new HashSet<>();
        private Set<String> mutation = new HashSet<>();
    }

    public Set<GraphqlResource> getIgnoreResource() {
        HashSet<GraphqlResource> result = new HashSet<>();

        for (Map.Entry<String, WhiteList> entry : graphql.entrySet()) {
            String uri = "/" + entry.getKey().replace("-", "/");
            WhiteList value = entry.getValue();
            for (String function : value.getQuery()) {
                GraphqlResource graphqlResource = new GraphqlResource();
                graphqlResource.setUri(uri);
                graphqlResource.setMethod(HttpMethod.POST.name());
                graphqlResource.setOperation("QUERY");
                graphqlResource.setFunctionName(function);
                result.add(graphqlResource);
            }
            for (String function : value.getMutation()) {
                GraphqlResource graphqlResource = new GraphqlResource();
                graphqlResource.setUri(uri);
                graphqlResource.setMethod(HttpMethod.POST.name());
                graphqlResource.setOperation("MUTATION");
                graphqlResource.setFunctionName(function);
                result.add(graphqlResource);
            }
        }
        return result;
    }
}
