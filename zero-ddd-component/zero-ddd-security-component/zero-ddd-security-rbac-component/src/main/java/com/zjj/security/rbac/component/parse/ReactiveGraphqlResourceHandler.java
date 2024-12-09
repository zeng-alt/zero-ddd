package com.zjj.security.rbac.component.parse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjj.autoconfigure.component.security.rbac.GraphqlResource;
import com.zjj.autoconfigure.component.security.rbac.Resource;
import graphql.language.Definition;
import graphql.language.Field;
import graphql.language.OperationDefinition;
import graphql.language.Selection;
import graphql.parser.Parser;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月05日 17:12
 */
public class ReactiveGraphqlResourceHandler extends AbstractReactiveResourceHandler {
    private ObjectMapper objectMapper;

    private ServerWebExchangeMatcher serverWebExchangeMatcher = new PathPatternParserServerWebExchangeMatcher("/*/graphql", HttpMethod.POST);

    public ReactiveGraphqlResourceHandler(ReactiveResourceQueryManager reactiveResourceQueryManager) {
        super(reactiveResourceQueryManager);

    }

    @Override
    public Mono<ServerWebExchangeMatcher.MatchResult> matcher(ServerWebExchange exchange) {
        return serverWebExchangeMatcher.matches(exchange);
    }

    @Override
    public Mono<Boolean> handler(ServerWebExchange exchange, Mono<Authentication> authentication) {
        return reactiveResourceQueryManager.authorize(create(exchange), authentication);
    }

    public Resource create(ServerWebExchange exchange) {
        GraphqlResource graphqlResource = new GraphqlResource();
        ServerHttpRequest request = exchange.getRequest();
        graphqlResource.setMethod(request.getMethod());
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
            if (definitions == null) return graphqlResource;
            OperationDefinition definition = (OperationDefinition) definitions.get(0);
            graphqlResource.setFunctionNames(new ArrayList<>());
            for (Selection selection : definition.getSelectionSet().getSelections()) {
                graphqlResource.getFunctionNames().add(((Field) selection).getName());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return graphqlResource;
    }
}
