package com.zjj.core.component.type;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjj.autoconfigure.component.json.JsonHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月27日 17:23
 */
@SpringBootTest
public class TestJsonConversionContext {

    @Autowired
    private JsonConversionContext jsonConversionContext;
    @Autowired
    private JsonHelper jsonHelper;

    @Test
    public void testConvertString() throws JsonProcessingException {
//        String convert = jsonConversionContext.convert(TargetType.STRING, "张三");
//        assertThat(convert).isEqualTo("张三");

        String jsonString = jsonHelper.toJsonString("张三");
        System.out.println(jsonString);
        System.out.println(jsonHelper.toJsonString(123));
        System.out.println(jsonHelper.toJsonString(123.123));
        System.out.println(jsonHelper.toJsonString(new BigDecimal("1234.23445")));
        System.out.println(jsonHelper.toJsonString(LocalDateTime.now()));
        System.out.println(jsonHelper.toJsonString(LocalDate.now()));
        System.out.println(jsonHelper.toJsonString(LocalTime.now()));
        System.out.println(jsonHelper.toJsonString(List.of(1233, "3445", "dg,,,dg")));

        String jsonString1 = "[1233,\"3445\",\"dg,,,dg\"]";  // 你的 JSON 字符串
        Object convert = TypeConversionHelper.convert("list", jsonString1);
        System.out.println(convert);
        // 创建 ObjectMapper 对象
        ObjectMapper objectMapper = new ObjectMapper();

        // 使用 ObjectMapper 将 JSON 字符串解析成 List<Object>
        List<Object> result = objectMapper.readValue(jsonString1, List.class);



        // 输出解析结果
        System.out.println(result);

        System.out.println(jsonHelper.parseObject(jsonString1, List.class));
    }
}
