package com.zjj.doc.config.properties;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.tags.Tag;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author zengJiaJun
 * @crateTime 2024年06月16日 22:12
 * @version 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "springdoc")
public class OpenApiProperties {

	private String devUrl;

	private String prodUrl;

	@NestedConfigurationProperty
	private InfoProperties infoProperties = new InfoProperties();

	/**
	 * 扩展文档地址
	 */
	@NestedConfigurationProperty
	private ExternalDocumentation externalDocs;

	/**
	 * 标签
	 */
	private List<Tag> tags = null;

	/**
	 * 组件
	 */
	@NestedConfigurationProperty
	private Components components = null;

	@NestedConfigurationProperty
	private Paths paths = null;

}
