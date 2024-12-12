package com.zjj.security.rbac.component.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjj.autoconfigure.component.security.rbac.RbacCacheManage;
import com.zjj.security.rbac.component.handler.*;
import com.zjj.security.rbac.component.locator.*;
import com.zjj.security.rbac.component.manager.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月09日 20:44
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class RbacWebAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(HttpResourceLocator.class)
	public HttpResourceLocator httpResourceLocator(ObjectProvider<RbacCacheManage> rbacCacheManage) {
		return new HttpResourceLocator(rbacCacheManage.getIfAvailable());
	}

	@Bean
	@ConditionalOnMissingBean(GraphqlResourceLocator.class)
	public GraphqlResourceLocator graphqlResourceLocator(ObjectProvider<RbacCacheManage> rbacCacheManage) {
		return new GraphqlResourceLocator(rbacCacheManage.getIfAvailable());
	}

	@Bean
	@ConditionalOnMissingBean(ResourceQueryManager.class)
	public ResourceQueryManager resourceQueryManager(ObjectProvider<ResourceLocator> resourceLocators) {
		return new ResourceQueryManager(resourceLocators.stream().toList());
	}

	@Bean
	@ConditionalOnMissingBean(GraphqlResourceHandler.class)
	public GraphqlResourceHandler graphqlResourceHandler(ResourceQueryManager resourceQueryManager, ObjectMapper objectMapper) {
		return new GraphqlResourceHandler(resourceQueryManager, objectMapper);
	}

	@Bean
	public ParseManager parseManager(ObjectProvider<ResourceHandler> resourceHandlers, ResourceQueryManager resourceQueryManager) {
		List<ResourceHandler> list = new ArrayList<>(resourceHandlers.orderedStream().toList());
		return new ParseManager(list, new HttpResourceHandler(resourceQueryManager));
	}

	@Bean
	public RbacAccessAuthorizationManager rbacAuthorizationManager(ParseManager parseManager) {
		return new RbacAccessAuthorizationManager(parseManager);
	}

//	@Bean
//	public SecurityBuilderCustomizer rbacCustomizer(
//			AuthorizationManager<RequestAuthorizationContext> rbacAuthorizationManager) {
//		return http -> http.authorizeHttpRequests(a -> a.anyRequest().access(rbacAuthorizationManager));
//	}

}
