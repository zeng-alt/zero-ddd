package com.zjj.security.rbac.client.component;

import com.zjj.autoconfigure.component.redis.RedisSubPubRepository;
import com.zjj.autoconfigure.component.security.rbac.GraphqlResource;
import graphql.language.FieldDefinition;
import graphql.language.ObjectTypeExtensionDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.graphql.execution.GraphQlSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月11日 15:12
 */
@Slf4j
@RequiredArgsConstructor
public class GraphqlTemplateSupper implements ApplicationContextAware, InitializingBean {

    private final EndpointPrefix endpointPrefix;
    private final RedisSubPubRepository redisSubPubRepository;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    public List<GraphqlResource> getGraphqlTemplate() {
        GraphQlSource graphQlSource = applicationContext.getBean(GraphQlSource.class);
        GraphQLSchema schema = graphQlSource.schema();
        GraphQLObjectType queryType = schema.getQueryType();
        List<GraphqlResource> query = this.transform(queryType, "Query");
        GraphQLObjectType mutationType = schema.getMutationType();
        List<GraphqlResource> mutation = this.transform(mutationType, "Mutation");
        GraphQLObjectType subscriptionType = schema.getSubscriptionType();
        List<GraphqlResource> subscription = this.transform(subscriptionType, "Subscription");

        return Stream.of(query, mutation, subscription).flatMap(List::stream).toList();
    }

    public void sendRouterTemplate() {
        List<GraphqlResource> graphqlTemplate = getGraphqlTemplate();
        redisSubPubRepository.publish(endpointPrefix.getPrefix() + "/graphql/template", graphqlTemplate);
        log.info("发送路由模板成功!!!!");
    }



    private List<GraphqlResource> transform(GraphQLObjectType graphQLObjectType, String type) {
        ArrayList<GraphqlResource> graphqlResources = new ArrayList<>();
        if (graphQLObjectType == null) {
            return graphqlResources;
        }
        for (ObjectTypeExtensionDefinition extensionDefinition : graphQLObjectType.getExtensionDefinitions()) {
            for (FieldDefinition fieldDefinition : extensionDefinition.getFieldDefinitions()) {
                String name = fieldDefinition.getName();

                GraphqlResource graphqlResource = new GraphqlResource();
                graphqlResource.setFunctionName(name);
                graphqlResource.setType(type);
                graphqlResource.setUri(endpointPrefix.getPrefix() + "/graphql");
                graphqlResources.add(graphqlResource);
            }
        }

        return graphqlResources;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(this::sendRouterTemplate).start();
    }
}
