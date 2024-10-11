package com.zjj.cache.component.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.extern.slf4j.Slf4j;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.CompositeCodec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.List;

/**
 * @author zengJiaJun
 * @crateTime 2024年06月16日 21:08
 * @version 1.0
 */
@Slf4j
@AutoConfiguration(after = JacksonAutoConfiguration.class)
public class RedisAutoConfiguration {

	@Bean
	public RedissonAutoConfigurationCustomizer redissonCustomizer(ObjectProvider<Module> modules, List<Jackson2ObjectMapperBuilderCustomizer> customizers) {
		return config -> {
			Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
			for (Jackson2ObjectMapperBuilderCustomizer customizer : customizers) {
				customizer.customize(builder);
			}
			CacheJackson2ObjectMapperBuilderCustomizer cacheJacksonCustomizer = new CacheJackson2ObjectMapperBuilderCustomizer();
			cacheJacksonCustomizer.customize(builder);
			ObjectMapper objectMapper = builder.build();
			objectMapper.registerModules(modules);
			objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//			// 指定序列化输入的类型，类必须是非final修饰的。序列化时将对象全类名一起保存下来
			objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);

			JsonJacksonCodec jsonCodec = new JsonJacksonCodec(objectMapper);
			// 组合序列化 key 使用 String 内容使用通用 json 格式
			CompositeCodec codec = new CompositeCodec(StringCodec.INSTANCE, jsonCodec, jsonCodec);
			// 缓存 Lua 脚本 减少网络传输(redisson 大部分的功能都是基于 Lua 脚本实现)
			config.setUseScriptCache(true).setCodec(codec);
			// if (SpringUtils.isVirtual()) {
			// config.setNettyExecutor(new VirtualThreadTaskExecutor("redisson-"));
			// }
			log.info("初始化 redis 配置");
		};
	}

	/**
	 * 异常处理器
	 */
	// @Bean
	// public RedisExceptionHandler redisExceptionHandler() {
	// return new RedisExceptionHandler();
	// }

}
