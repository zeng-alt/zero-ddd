package com.zjj.json.component.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.zjj.autoconfigure.component.json.JsonHelper;
import com.zjj.json.component.conver.BigNumberConverter;
import com.zjj.json.component.utils.JacksonHelper;
import io.vavr.jackson.datatype.VavrModule;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年07月05日 20:44
 */
@Configuration
@AutoConfiguration(before = JacksonAutoConfiguration.class)
public class JsonConfig {

    @Bean
    @ConditionalOnMissingBean
    public JsonHelper jsonHelper() {
        return new JacksonHelper();
    }

    @Bean
    @DependsOn({"vavrTimeModule", "javaTimeModule"})
    public ObjectMapper objectMapper(List<Module> moduleList) {
        return new ObjectMapper().registerModules(moduleList);
    }

    @Bean
    public Module vavrTimeModule() {
        return new VavrModule();
    }

    @Bean
    public Module javaTimeModule() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(Long.class, BigNumberConverter.INSTANCE);
        javaTimeModule.addSerializer(Long.TYPE, BigNumberConverter.INSTANCE);
        javaTimeModule.addSerializer(BigInteger.class, BigNumberConverter.INSTANCE);
        javaTimeModule.addSerializer(BigDecimal.class, ToStringSerializer.instance);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
        return javaTimeModule;
    }
}
