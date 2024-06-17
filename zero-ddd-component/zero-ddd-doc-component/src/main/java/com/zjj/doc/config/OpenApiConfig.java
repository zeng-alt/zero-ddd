package com.zjj.doc.config;

import com.zjj.doc.config.properties.InfoProperties;
import com.zjj.doc.config.properties.OpenApiProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zengJiaJun
 * @crateTime 2024年06月16日 22:11
 * @version 1.0
 */
@Configuration
@EnableConfigurationProperties(OpenApiProperties.class)
@ConditionalOnProperty(name = "springdoc.api-docs.enabled", havingValue = "true", matchIfMissing = true)
public class OpenApiConfig {

    @Bean
    @ConditionalOnMissingBean(OpenAPI.class)
    public OpenAPI openApi(OpenApiProperties openApiProperties) {
        Server devServer = new Server();
        devServer.setUrl(openApiProperties.getDevUrl());
        devServer.setDescription("开发环境");

        Server prodServer = new Server();
        prodServer.setUrl(openApiProperties.getProdUrl());
        devServer.setDescription("生产环境");

        List<SecurityRequirement> list = new ArrayList<>();
        Set<String> keySet = openApiProperties.getComponents().getSecuritySchemes().keySet();
        SecurityRequirement securityRequirement = new SecurityRequirement();
        keySet.forEach(securityRequirement::addList);
        list.add(securityRequirement);

        return new OpenAPI()
                .info(info(openApiProperties.getInfoProperties()))
                .externalDocs(openApiProperties.getExternalDocs())
                .tags(openApiProperties.getTags())
                .paths(openApiProperties.getPaths())
                .components(openApiProperties.getComponents())
                .addServersItem(devServer)
                .addServersItem(prodServer)
                .security(list);
    }

    private Info info(InfoProperties infoProperties) {
        Info info = new Info();
        info.setTitle(infoProperties.getTitle());
        info.setDescription(infoProperties.getDescription());
        info.setContact(infoProperties.getContact());
        info.setLicense(infoProperties.getLicense());
        info.setVersion(infoProperties.getVersion());
        return info;
    }
}
