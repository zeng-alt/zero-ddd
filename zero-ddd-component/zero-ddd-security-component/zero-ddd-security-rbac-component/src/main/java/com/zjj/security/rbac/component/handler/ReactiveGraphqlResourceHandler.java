package com.zjj.security.rbac.component.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjj.autoconfigure.component.security.rbac.GraphqlResource;
import com.zjj.autoconfigure.component.security.rbac.Resource;
import com.zjj.bean.componenet.BeanHelper;
import com.zjj.security.rbac.component.manager.ReactiveResourceQueryManager;
import graphql.language.Definition;
import graphql.language.Field;
import graphql.language.OperationDefinition;
import graphql.language.Selection;
import graphql.parser.Parser;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月05日 17:12
 */
public class ReactiveGraphqlResourceHandler extends AbstractReactiveResourceHandler {
    private final ObjectMapper objectMapper;

    private ServerWebExchangeMatcher serverWebExchangeMatcher = new PathPatternParserServerWebExchangeMatcher("/*/graphql", HttpMethod.POST);

    public ReactiveGraphqlResourceHandler(ReactiveResourceQueryManager reactiveResourceQueryManager, ObjectMapper objectMapper) {
        super(reactiveResourceQueryManager);
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<ServerWebExchangeMatcher.MatchResult> matcher(ServerWebExchange exchange) {
        return serverWebExchangeMatcher.matches(exchange);
    }

    @Override
    public Mono<Boolean> handler(Mono<Authentication> authentication, AuthorizationContext object) {

        return reactiveResourceQueryManager
                .query(new GraphqlResource(), authentication)
                .flatMap(resources -> {
                    Set<Resource> targetResource = create(object.getExchange());
                    if (targetResource.isEmpty()) {
                        return Mono.just(false);
                    }
                    // 如果resources包含所有的targetResource返回true, 如果有一个没有包含就返回假
                    Set<Resource> collect = new HashSet<>(resources);
                    for (Resource resource : targetResource) {
                        if (!collect.contains(resource)) {
                            return Mono.just(false);
                        }
                    }
                    return Mono.just(true);
                }).switchIfEmpty(Mono.just(false));
    }

    public Set<Resource> create(ServerWebExchange exchange) {

        Set<Resource> result = new HashSet<>();

        GraphqlResource graphqlResource = new GraphqlResource();
        ServerHttpRequest request = exchange.getRequest();
        graphqlResource.setMethod(request.getMethod().name());
        graphqlResource.setUri(request.getURI().getPath());
        String body = DataBufferUtils.join(request.getBody()).flatMap(
                dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    return Mono.just(new String(bytes, StandardCharsets.UTF_8));
                }
        ).block();

        try {
            JsonNode jsonNode = objectMapper.readTree(body);
            Iterator<String> stringIterator = jsonNode.fieldNames();
            String json = "";
            if (stringIterator.hasNext()) {
                graphqlResource.setType(stringIterator.next());
                json = jsonNode.get(graphqlResource.getType()).asText();
            }
            List<Definition> definitions = new Parser().parseDocument(json).getDefinitions();
            if (definitions == null) return result;
            OperationDefinition definition = (OperationDefinition) definitions.get(0);
            for (Selection selection : definition.getSelectionSet().getSelections()) {
                graphqlResource.setFunctionName(((Field) selection).getName());
                result.add(BeanHelper.copyToObject(graphqlResource, GraphqlResource.class));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
