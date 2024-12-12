package com.zjj.security.rbac.component.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjj.autoconfigure.component.security.rbac.GraphqlResource;
import com.zjj.autoconfigure.component.security.rbac.Resource;
import com.zjj.security.rbac.component.manager.ResourceQueryManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月05日 17:12
 */

public class GraphqlResourceHandler extends AbstractResourceHandler {
    private final ObjectMapper objectMapper;

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/*/graphql",
            "POST");

    public GraphqlResourceHandler(ResourceQueryManager resourceQueryManager, ObjectMapper objectMapper) {
        super(resourceQueryManager);
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean matcher(HttpServletRequest request) {
        return DEFAULT_ANT_PATH_REQUEST_MATCHER.matches(request);
    }

    @Override
    public Boolean handler(Authentication authentication, RequestAuthorizationContext object) {

        List<Resource> resources = resourceQueryManager.query(new GraphqlResource(), authentication);
        Set<Resource> targetResource = create(object);
        if (targetResource.isEmpty()) {
            return false;
        }
        // 如果resources包含所有的targetResource返回true, 如果有一个没有包含就返回假
        Set<Resource> collect = new HashSet<>(resources);
        for (Resource resource : targetResource) {
            if (!collect.contains(resource)) {
                return false;
            }
        }
        return true;
    }

    public Set<Resource> create(RequestAuthorizationContext object) {

        Set<Resource> result = new HashSet<>();

        GraphqlResource graphqlResource = new GraphqlResource();
        HttpServletRequest request = object.getRequest();
        graphqlResource.setMethod(request.getMethod());
        graphqlResource.setUri(request.getRequestURI());

//        String body = DataBufferUtils.join(request.getBody()).flatMap(
//                dataBuffer -> {
//                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
//                    dataBuffer.read(bytes);
//                    DataBufferUtils.release(dataBuffer);
//                    return Mono.just(new String(bytes, StandardCharsets.UTF_8));
//                }
//        ).block();
//
//        try {
//            JsonNode jsonNode = objectMapper.readTree(body);
//            Iterator<String> stringIterator = jsonNode.fieldNames();
//            String json = "";
//            if (stringIterator.hasNext()) {
//                graphqlResource.setType(stringIterator.next());
//                json = jsonNode.get(graphqlResource.getType()).asText();
//            }
//            List<Definition> definitions = new Parser().parseDocument(json).getDefinitions();
//            if (definitions == null) return result;
//            OperationDefinition definition = (OperationDefinition) definitions.get(0);
//            for (Selection selection : definition.getSelectionSet().getSelections()) {
//                graphqlResource.setFunctionName(((Field) selection).getName());
//                result.add(BeanHelper.copyToObject(graphqlResource, GraphqlResource.class));
//            }
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
        return result;
    }
}
